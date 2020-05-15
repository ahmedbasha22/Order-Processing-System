package application.model;

import java.sql.SQLException;
import java.util.List;

public interface Driver {

	public boolean alreadyRegistredUsername(String username) throws SQLException;

	public boolean alreadyRegistredEmail(String email) throws SQLException;

	public void addNewUser(User newUser) throws SQLException;

	public User getUser(String username, String password) throws SQLException;

	public boolean authenticateUser(String username, String password) throws SQLException;

	public User modifyExistingUser(String oldUsername, String oldPassword, User newUserInfo) throws SQLException;

	public void promoteUser(String userName) throws SQLException;

	public void addNewBook(Book newBook) throws SQLException;

	public boolean isPublisherExist(String publisherName) throws SQLException;
	
	public void addNewPublisher(Publisher publisher) throws SQLException;

	public Book modifyBookISBN(int oldISBN, int newISBN) throws SQLException;

	public Book modifyBookTitle(int ISBN, String newTitle) throws SQLException;

	public Book modifyBookAuthors(int ISBN, List<String> newAuthors) throws SQLException;

	public Book modifyBookSellingPrice(int ISBN, double newSellingPrice) throws SQLException;

	public Book modifyBookCategory(int ISBN, String newCategory) throws SQLException;

	public Book modifyBookQuantity(int ISBN, int newQuantity) throws SQLException;

	public Book modifyBookMinimumQuantity(int ISBN, int newMinimumQuantity) throws SQLException;

	public Book modifyBookPublicationYear(int ISBN, int newPublicationYear) throws SQLException;

	public Book modifyBookPublisherName(int ISBN, String newPublisherName) throws SQLException;

	public Publisher modifyPublisher(String oldPublisherName, Publisher newPublisherInfo) throws SQLException;

	public Book orderMoreQuantity(int ISBN, int addedQuantity) throws SQLException;

	public List<Book> getAllBooks() throws SQLException;
	
	public List<Book> getBooksByISBN(int ISBN) throws SQLException;

	public List<Book> getBooksByTitle(String title) throws SQLException;

	public List<Book> getBooksByAuthor(String author) throws SQLException;

	public List<Book> getBooksBySellingPrice(double sellingPrice) throws SQLException;

	public List<Book> getBooksByCatgory(String category) throws SQLException;

	public List<Book> getBooksByPublicationYear(int publicationYear) throws SQLException;

	public List<Book> getBooksByPublisherName(String publisherName) throws SQLException;

	public Publisher getPublisher(String publisherName) throws SQLException;

	public List<Book> addBookToShoppingCart(String username, int bookISBN, int quantity) throws SQLException;

	public List<Book> getShoppingCart(String userName) throws SQLException;
		
	public List<Book> modifyBookInShoppingCart(String username, int bookISBN, int newQuantity) throws SQLException;

	public List<Book> removeBookFromShoppingCart(String username, int bookISBN) throws SQLException;

	public void clearShoppingCart(String username) throws SQLException;

	public void checkoutShoppingCart(String username) throws SQLException;

	public List<Book> getBookSalesPreviousMonth() throws SQLException;

	public List<User> getTop5Users() throws SQLException;

	public List<Book> getUserPurchases(String username) throws SQLException;

	public List<Book> getTop10SoldBooks() throws SQLException;

	public void close() throws SQLException;
}
