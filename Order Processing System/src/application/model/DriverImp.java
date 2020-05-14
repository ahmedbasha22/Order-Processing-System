package application.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DriverImp implements Driver {
	Connection connection;

	public DriverImp() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_store", "admin", "1234");
	}

	@Override
	public boolean AlreadyRegistredUsername(String username) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("select user_name from user where user_name = ?");
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		boolean result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public boolean AlreadyRegistredEmail(String email) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("select email from user where email = ?");
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		boolean result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public void addNewUser(User newUser) throws SQLException {
		CallableStatement statement = connection.prepareCall("{CALL add_new_user(?,?,?,?,?,?,?,?)}");
		statement.setString(1, newUser.getUsername());
		statement.setString(2, newUser.getEmail());
		statement.setString(3, newUser.getPassword());
		statement.setString(4, newUser.getLastName());
		statement.setString(5, newUser.getFirstName());
		statement.setString(6, newUser.getPhone());
		statement.setString(7, newUser.getShppingAddress());
		statement.setInt(8, newUser.isManager() ? 1 : 0);
		statement.execute();
		statement.close();
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("select * from user where user_name = ? and password = MD5(?)");
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		User user = null;
		if (resultSet.next()) {
			user = new User(resultSet.getString(1), resultSet.getString(2), null, resultSet.getString(4),
					resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
			PreparedStatement statement2 = connection.prepareStatement("select * from manager where user_name = ?");
			statement2.setString(1, username);
			ResultSet resultSet2 = statement2.executeQuery();
			user.setManager(resultSet.next());
			resultSet2.close();
			statement2.close();
		}
		resultSet.close();
		statement.close();
		return user;
	}

	@Override
	public boolean validateUser(String username, String password) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("select user_name from user where user_name = ? and password = MD5(?)");
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		boolean result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public User modifyExistingUser(String oldUsername, String oldPassword, User newUserInfo) throws SQLException {
		if (validateUser(oldUsername, oldPassword)) {
			throw new SQLException("User doesn't exist!!");
		}
		if (newUserInfo.getPassword() == null)
			newUserInfo.setPassword(oldPassword);
		CallableStatement statement = connection.prepareCall("{CALL modify_user(?,?,?,?,?,?,?,?,?)}");
		statement.setString(1, newUserInfo.getUsername());
		statement.setString(2, newUserInfo.getEmail());
		statement.setString(3, newUserInfo.getPassword());
		statement.setString(4, newUserInfo.getLastName());
		statement.setString(5, newUserInfo.getFirstName());
		statement.setString(6, newUserInfo.getPhone());
		statement.setString(7, newUserInfo.getShppingAddress());
		statement.setInt(8, newUserInfo.isManager() ? 1 : 0);
		statement.setString(9, oldUsername);
		statement.execute();
		statement.close();
		return getUser(newUserInfo.getUsername(), newUserInfo.getPassword());
	}

	@Override
	public void promoteUser(String userName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("insert into manager values(?)");
		statement.setString(1, userName);
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void addNewBook(Book newBook, int minimumQuantity) throws SQLException {
		connection.setAutoCommit(false);
		try {
			PreparedStatement insertStatement = connection.prepareStatement("insert into book values(?,?,?,?,?,?,?,?)");
			insertStatement.setInt(1, newBook.getISBN());
			insertStatement.setString(2, newBook.getTitle());
			insertStatement.setInt(3, newBook.getPublicationYear());
			insertStatement.setDouble(4, newBook.getSellingPrice());
			insertStatement.setString(5, newBook.getCategory());
			insertStatement.setInt(6, newBook.getQuantity());
			insertStatement.setInt(7, minimumQuantity);
			insertStatement.setString(8, newBook.getPublisherName());
			insertStatement.executeUpdate();
			insertStatement.close();

			PreparedStatement insertAuthor = connection.prepareStatement("insert into book_author values(?,?)");
			insertAuthor.setInt(1, newBook.getISBN());
			for (String authorName : newBook.getAuthors()) {
				insertAuthor.setString(2, authorName);
				insertAuthor.executeUpdate();
			}
			insertAuthor.close();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			connection.rollback();
			connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public void addNewPublisher(Publisher publisher) throws SQLException {
		PreparedStatement insertStatement = connection.prepareStatement("insert into publisher values(?,?,?)");
		insertStatement.setString(1, publisher.getName());
		insertStatement.setString(2, publisher.getAddress());
		insertStatement.setString(3, publisher.getPhone());
		insertStatement.executeUpdate();
		insertStatement.close();
	}

	@Override
	public Book modifyBookISBN(int oldISBN, int newISBN) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set ISBN = ? where ISBN = ?");
		statement.setInt(1, newISBN);
		statement.setInt(2, oldISBN);
		statement.close();
		return getBooksByISBN(newISBN).get(0);
	}

	@Override
	public Book modifyBookTitle(int ISBN, String newTitle) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set title = ? where ISBN = ?");
		statement.setString(1, newTitle);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookAuthors(int ISBN, List<String> newAuthors) throws SQLException {
		connection.setAutoCommit(false);
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("delete from book_author where ISBN = " + ISBN);
			stmt.close();

			PreparedStatement insertAuthor = connection.prepareStatement("insert into book_author values(?,?)");
			insertAuthor.setInt(1, ISBN);
			for (String authorName : newAuthors) {
				insertAuthor.setString(2, authorName);
				insertAuthor.executeUpdate();
			}
			insertAuthor.close();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			connection.rollback();
			connection.setAutoCommit(true);
			throw e;
		}
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookSellingPrice(int ISBN, double newSellingPrice) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set selling_price = ? where ISBN = ?");
		statement.setDouble(1, newSellingPrice);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookCategory(int ISBN, String newCategory) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set category = ? where ISBN = ?");
		statement.setString(1, newCategory);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookQuantity(int ISBN, int newQuantity) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set quantity = ? where ISBN = ?");
		statement.setInt(1, newQuantity);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}
	
	@Override
	public Book modifyBookMinimumQuantity(int ISBN, int newMinimumQuantity) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("update book set Minimum_quantity = ? where ISBN = ?");
		statement.setInt(1, newMinimumQuantity);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}


	@Override
	public Book modifyBookPublicationYear(int ISBN, int newPublicationYear) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set publication_year = ? where ISBN = ?");
		statement.setInt(1, newPublicationYear);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookPublisherName(int ISBN, String newPublisherName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set publisher_name = ? where ISBN = ?");
		statement.setString(1, newPublisherName);
		statement.setInt(2, ISBN);
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Publisher modifyPublisher(String oldPublisherName, Publisher newPublisherInfo) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("update publisher set name = ?, address = ?,phone_number = ? where name = ?");
		statement.setString(1, newPublisherInfo.getName());
		statement.setString(2, newPublisherInfo.getAddress());
		statement.setString(3, newPublisherInfo.getPhone());
		statement.setString(4, oldPublisherName);
		statement.executeUpdate();
		statement.close();
		return newPublisherInfo;
	}

	@Override
	public Book orderMoreQuantity(int ISBN, int addedQuantity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMinimumQuantity(int ISBN) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Book> getBooksByISBN(int ISBN) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByTitle(String title) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByAuthor(String author) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksBySellingPrice(double sellingPrice) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByCatgory(String category) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByPublicationYear(int publicationYear) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByPublisherName(String publisherName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publisher getPublisher(String publisherName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> addBookToShoppingCart(String username, int bookISBN, int quantity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> modifyBookInShoppingCart(String username, int bookISBN, int newQuantity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> removeBookFromShppingCart(String username, int bookISBN) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearShoppingCart(String username) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkoutShoppingCart(String username) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> getBookSalesPreviousMonth() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getTop5Users() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getUserPurchases(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getTop10SoldBooks() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws SQLException {
		connection.close();
	}

}
