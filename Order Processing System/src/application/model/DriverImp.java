package application.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverImp implements Driver {
	private static Driver instance;
	private Connection connection;

	public static Driver getInstance() throws SQLException {
		if (instance == null)
			instance = new DriverImp();
		return instance;
	}

	private DriverImp() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_store", "admin", "1234");
	}

	@Override
	public boolean alreadyRegistredUsername(String username) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("select user_name from user where user_name = ?");
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		boolean result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public boolean alreadyRegistredEmail(String email) throws SQLException {
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
	public boolean authenticateUser(String username, String password) throws SQLException {
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
		if (authenticateUser(oldUsername, oldPassword)) {
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
	public void addNewBook(Book newBook) throws SQLException {
		connection.setAutoCommit(false);
		try {
			PreparedStatement insertStatement = connection.prepareStatement("insert into book values(?,?,?,?,?,?,?,?)");
			insertStatement.setInt(1, newBook.getISBN());
			insertStatement.setString(2, newBook.getTitle());
			insertStatement.setInt(3, newBook.getPublicationYear());
			insertStatement.setDouble(4, newBook.getSellingPrice());
			insertStatement.setString(5, newBook.getCategory());
			insertStatement.setInt(6, newBook.getQuantity());
			insertStatement.setInt(7, newBook.getMinQuantity());
			insertStatement.setString(8, newBook.getPublisherName());
			insertStatement.executeUpdate();
			insertStatement.close();

			PreparedStatement insertAuthor = connection.prepareStatement("insert into book_author values(?,?)");
			insertAuthor.setInt(1, newBook.getISBN());
			//TODO uncomment
//			for (String authorName : newBook.getAuthors()) {
//				insertAuthor.setString(2, authorName);
//				insertAuthor.executeUpdate();
//			}

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
	public boolean isPublisherExist(String publisherName) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("select name from publisher where name = ?");;
		statement.setString(1, publisherName);
		ResultSet resultSet = statement.executeQuery();
		boolean result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
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
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(newISBN).get(0);
	}

	@Override
	public Book modifyBookTitle(int ISBN, String newTitle) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set title = ? where ISBN = ?");
		statement.setString(1, newTitle);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
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
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookCategory(int ISBN, String newCategory) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set category = ? where ISBN = ?");
		statement.setString(1, newCategory);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookQuantity(int ISBN, int newQuantity) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set quantity = ? where ISBN = ?");
		statement.setInt(1, newQuantity);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookMinimumQuantity(int ISBN, int newMinimumQuantity) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("update book set Minimum_quantity = ? where ISBN = ?");
		statement.setInt(1, newMinimumQuantity);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookPublicationYear(int ISBN, int newPublicationYear) throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("update book set publication_year = ? where ISBN = ?");
		statement.setInt(1, newPublicationYear);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Override
	public Book modifyBookPublisherName(int ISBN, String newPublisherName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("update book set publisher_name = ? where ISBN = ?");
		statement.setString(1, newPublisherName);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
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
		PreparedStatement statement = connection
				.prepareStatement("update book set quantity = quantity + ? where ISBN = ?");
		statement.setInt(1, addedQuantity);
		statement.setInt(2, ISBN);
		statement.executeUpdate();
		statement.close();
		return getBooksByISBN(ISBN).get(0);
	}

	@Deprecated
	public int getMinimumQuantity(int ISBN) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery("SELECT Minimum_quantity FROM book Where ISBN = " + ISBN);
		res.next();
		int minQuantity = res.getInt("Minimum_quantity");
		res.close();
		stmt.close();
		return minQuantity;
	}

	@Override
	public List<Book> getAllBooks() throws SQLException {
		connection.setAutoCommit(false);
		try {
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM book");
			List<Book> bookList = convertResultSetIntoBooks(res);
			res.close();
			stmt.close();
			connection.commit();
			connection.setAutoCommit(true);
			return bookList;
		} catch (SQLException e) {
			connection.rollback();
			connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public List<Book> getBooksByISBN(int ISBN) throws SQLException {
		return getBookBy("ISBN", Integer.toString(ISBN));
	}

	private List<Book> getBookBy(String attribute, String value) throws SQLException {
		connection.setAutoCommit(false);
		try {
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM book Where " + attribute + " LIKE " + "'" + value + "%'");
			List<Book> bookList = convertResultSetIntoBooks(res);
			res.close();
			stmt.close();
			connection.commit();
			connection.setAutoCommit(true);
			return bookList;
		} catch (SQLException e) {
			connection.rollback();
			connection.setAutoCommit(true);
			throw e;
		}
	}

	private List<Book> convertResultSetIntoBooks(ResultSet res) throws SQLException {
		List<Book> bookList = new ArrayList<>();
		while (res.next()) {
			int ISBN = res.getInt("ISBN");
			Statement stmt = connection.createStatement();
			ResultSet authorSet = stmt.executeQuery("SELECT author_name FROM book_author where ISBN = " + ISBN);
			List<String> authorList = convertResultSetIntoAuthors(authorSet);
			authorSet.close();
			stmt.close();
			bookList.add(new Book(ISBN, res.getString("Title"), res.getInt("publication_year"),
					res.getDouble("selling_price"), res.getString("category"), res.getInt("quantity"),
					res.getString("publisher_name"), authorList, res.getInt("Minimum_quantity")));
		}
		return bookList;
	}

	private List<String> convertResultSetIntoAuthors(ResultSet res) throws SQLException {
		List<String> authorList = new ArrayList<>();
		while (res.next()) {
			authorList.add(res.getString("author_name"));
		}
		return authorList;
	}

	@Override
	public List<Book> getBooksByTitle(String title) throws SQLException {
		return getBookBy("Title", title);
	}

	@Override
	public List<Book> getBooksByAuthor(String author) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet res = stmt
				.executeQuery("SELECT distinct ISBN FROM book_author where author_name LIKE " + "'" + author + "%'");
		List<Book> bookList = new ArrayList<>();
		while (res.next()) {
			bookList.addAll(getBooksByISBN(res.getInt("ISBN")));
		}
		res.close();
		stmt.close();
		return bookList;
	}

	@Override
	public List<Book> getBooksBySellingPrice(double sellingPrice) throws SQLException {
		return getBookBy("selling_price", Double.toString(sellingPrice));
	}

	@Override
	public List<Book> getBooksByCatgory(String category) throws SQLException {
		return getBookBy("category", category);
	}

	@Override
	public List<Book> getBooksByPublicationYear(int publicationYear) throws SQLException {
		return getBookBy("Publication_year", Integer.toString(publicationYear));
	}

	@Override
	public List<Book> getBooksByPublisherName(String publisherName) throws SQLException {
		return getBookBy("publisher_name", publisherName);
	}

	@Override
	public Publisher getPublisher(String publisherName) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery("SELECT * FROM publisher Where name = " + "'" + publisherName + "'");
		res.next();
		Publisher publisher = new Publisher(res.getString(1), res.getString(2), res.getString(3));
		res.close();
		stmt.close();
		return publisher;
	}

	@Override
	public List<Book> addBookToShoppingCart(String username, int bookISBN, int quantity) throws SQLException {
		PreparedStatement insertStatement = connection.prepareStatement("insert into shopping_cart values(?,?,?)");
		insertStatement.setInt(1, bookISBN);
		insertStatement.setString(2, username);
		insertStatement.setInt(3, quantity);
		insertStatement.executeUpdate();
		insertStatement.close();
		return getShoppingCart(username);
	}

	@Override
	public List<Book> getShoppingCart(String userName) throws SQLException {
		Map<Integer, Integer> quantityMap = new HashMap<>();
		List<Book> bookList = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet res = stmt
				.executeQuery("SELECT ISBN, quantity FROM shopping_cart Where user_name = " + "'" + userName + "'");
		while (res.next()) {
			quantityMap.put(res.getInt("ISBN"), res.getInt("quantity"));
			bookList.addAll(getBooksByISBN(res.getInt("ISBN")));
		}
		res.close();
		stmt.close();
		for (Book book : bookList) {
			book.setQuantity(quantityMap.get(book.getISBN()));
		}
		return bookList;
	}

	@Override
	public List<Book> modifyBookInShoppingCart(String username, int bookISBN, int newQuantity) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("update shopping_cart set quantity = ? where ISBN = ? and user_name = ?");
		stmt.setInt(1, newQuantity);
		stmt.setInt(2, bookISBN);
		stmt.setString(3, username);
		stmt.executeUpdate();
		stmt.close();
		return getShoppingCart(username);
	}

	@Override
	public List<Book> removeBookFromShoppingCart(String username, int bookISBN) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("DELETE FROM shopping_cart where ISBN = ? and user_name = ?");
		stmt.setInt(1, bookISBN);
		stmt.setString(2, username);
		stmt.executeUpdate();
		stmt.close();
		return getShoppingCart(username);
	}

	@Override
	public void clearShoppingCart(String username) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM shopping_cart where user_name = ?");
		stmt.setString(1, username);
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void checkoutShoppingCart(String username) throws SQLException {
		connection.setAutoCommit(false);
		try {
			List<Book> shoppingCart = getShoppingCart(username);
			PreparedStatement stmt = connection.prepareStatement("Insert Into sale values(?,?,?,?)");
			Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
			stmt.setString(2, username);
			stmt.setDate(4, currentDate);
			for (Book book : shoppingCart) {
				stmt.setInt(1, book.getISBN());
				stmt.setInt(3, book.getQuantity());
				stmt.executeUpdate();
			}
			stmt.close();
			clearShoppingCart(username);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			connection.rollback();
			connection.setAutoCommit(true);
			throw e;
		}
	}

	private void deleteSalesBefore3Months() throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("DELETE FROM sale WHERE Date < DATE_SUB(CURDATE(), INTERVAL 3 MONTH)");
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public List<Book> getBookSalesPreviousMonth() throws SQLException {
		deleteSalesBefore3Months();
		return getBooksfromSales(
				"SELECT ISBN, sum(quantity) FROM sale Where Date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) GROUP BY ISBN");
	}

	private List<Book> getBooksfromSales(String sql) throws SQLException {
		Map<Integer, Integer> quantityMap = new HashMap<>();
		List<Book> bookList = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			quantityMap.put(res.getInt("ISBN"), res.getInt("sum(quantity)"));
			bookList.addAll(getBooksByISBN(res.getInt("ISBN")));
		}
		res.close();
		stmt.close();
		for (Book book : bookList) {
			book.setQuantity(quantityMap.get(book.getISBN()));
		}
		return bookList;
	}

	@Override
	public List<User> getTop5Users() throws SQLException {
		deleteSalesBefore3Months();
		List<User> userList = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet nameResultSet = stmt.executeQuery(
				"SELECT username, sum(quantity) FROM sale GROUP BY username ORDER BY sum(quantity) DESC LIMIT 5");
		PreparedStatement statement = connection.prepareStatement("select * from user where user_name = ?");
		while (nameResultSet.next()) {
			statement.setString(1, nameResultSet.getString("username"));
			ResultSet userResultSet = statement.executeQuery();
			userResultSet.next();
			User user = new User(userResultSet.getString(1), userResultSet.getString(2), null,
					userResultSet.getString(4), userResultSet.getString(5), userResultSet.getString(6),
					userResultSet.getString(7));
			PreparedStatement statement2 = connection.prepareStatement("select * from manager where user_name = ?");
			statement2.setString(1, nameResultSet.getString("username"));
			ResultSet resultSet2 = statement2.executeQuery();
			user.setManager(userResultSet.next());
			resultSet2.close();
			statement2.close();
			userResultSet.close();
			userList.add(user);
		}
		statement.close();
		nameResultSet.close();
		stmt.close();
		return userList;
	}

	@Override
	public List<Book> getUserPurchases(String username) throws SQLException {
		deleteSalesBefore3Months();
		return getBooksfromSales(
				"SELECT ISBN, sum(quantity) FROM sale Where user_name = " + "'" + username + "' GROUP BY ISBN");
	}

	@Override
	public List<Book> getTop10SoldBooks() throws SQLException {
		deleteSalesBefore3Months();
		return getBooksfromSales(
				"SELECT ISBN, sum(quantity) FROM sale GROUP BY ISBN ORDER BY sum(quantity) DESC LIMIT 10");
	}

	@Override
	public void close() throws SQLException {
		connection.close();
	}

}
