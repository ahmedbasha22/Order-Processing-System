package application.model;

import java.util.List;

public class DriverImp implements Driver {

	@Override
	public boolean AlreadyRegistredUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean AlreadyRegistredEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addNewUser(User newUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public User getUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User modifyExistingUser(String oldUsername, String oldPassword, User newUserInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void promoteUser(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewBook(Book newBook, int minimumQuantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Book modifyExistingBook(int oldISBN, Book newBookInfo, Integer newMinimumQuantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book orderMoreQuantity(int ISBN, int addedQuantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByISBN(int ISBN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksBySellingPrice(int sellingPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByCatgory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByPublicationYear(int publicationYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByPublisherName(String publisherName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> addBookToShoppingCart(String username, int bookISBN, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> modifyBookInShoppingCart(String username, int bookISBN, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> removeBookFromShppingCart(String username, int bookISBN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkoutShoppingCart(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> getBookSalesPreviousMonth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getTop5Users() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getUserPurchases(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getTop10SoldBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
