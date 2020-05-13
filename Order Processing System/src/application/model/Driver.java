package application.model;

import java.sql.SQLException;
import java.util.List;

public interface Driver  {

	public boolean AlreadyRegistredUsername(String username) throws SQLException;
	
	public boolean AlreadyRegistredEmail(String email) throws SQLException;
		
	public void addNewUser(User newUser) throws SQLException;
	
	public User getUser(String username, String password) throws SQLException;

	public boolean validateUser(String username, String password) throws SQLException;

	public User modifyExistingUser(String oldUsername, String oldPassword, User newUserInfo) throws SQLException;
	
	public void promoteUser(String userName) throws SQLException;
	
	public void addNewBook(Book newBook, int minimumQuantity) throws SQLException;
	
	public Book modifyExistingBook(int oldISBN, Book newBookInfo, Integer newMinimumQuantity) throws SQLException;
	
	public Book orderMoreQuantity(int ISBN, int addedQuantity) throws SQLException;
	
	public List<Book> getBooksByISBN(int ISBN) throws SQLException;
	
	public List<Book> getBooksByTitle(String title) throws SQLException;
	
	public List<Book> getBooksByAuthor(String author) throws SQLException;
	
	public List<Book> getBooksBySellingPrice(int sellingPrice) throws SQLException;
	
	public List<Book> getBooksByCatgory(String category) throws SQLException;

	public List<Book> getBooksByPublicationYear(int publicationYear) throws SQLException;
	
	public List<Book> getBooksByPublisherName(String publisherName) throws SQLException;
	
	public List<Book> addBookToShoppingCart(String username, int bookISBN, int quantity) throws SQLException;
	
	public List<Book> modifyBookInShoppingCart(String username, int bookISBN, int quantity) throws SQLException;

	public List<Book> removeBookFromShppingCart(String username, int bookISBN) throws SQLException;
	
	public void checkoutShoppingCart(String username) throws SQLException;

	public List<Book> getBookSalesPreviousMonth() throws SQLException;
	
	public List<User> getTop5Users() throws SQLException;
	
	public List<Book> getUserPurchases(String username) throws SQLException;
	
	public List<Book> getTop10SoldBooks() throws SQLException;
	
	public void close() throws SQLException;
}
