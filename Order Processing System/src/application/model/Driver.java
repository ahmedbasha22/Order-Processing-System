package application.model;

import java.util.List;

public interface Driver {

	public boolean AlreadyRegistredUsername(String username);
	
	public boolean AlreadyRegistredEmail(String email);
		
	public void addNewUser(User newUser);
	
	public User getUser(String username, String password);

	public boolean validateUser(String username, String password);

	public User modifyExistingUser(String oldUsername, String oldPassword, User newUserInfo);
	
	public void promoteUser(String userName);
	
	public void addNewBook(Book newBook, int minimumQuantity);
	
	public Book modifyExistingBook(int oldISBN, Book newBookInfo, Integer newMinimumQuantity);
	
	public Book orderMoreQuantity(int ISBN, int addedQuantity);
	
	public List<Book> getBooksByISBN(int ISBN);
	
	public List<Book> getBooksByTitle(String title);
	
	public List<Book> getBooksByAuthor(String author);
	
	public List<Book> getBooksBySellingPrice(int sellingPrice);
	
	public List<Book> getBooksByCatgory(String category);

	public List<Book> getBooksByPublicationYear(int publicationYear);
	
	public List<Book> getBooksByPublisherName(String publisherName);
	
	public List<Book> addBookToShoppingCart(String username, int bookISBN, int quantity);
	
	public List<Book> modifyBookInShoppingCart(String username, int bookISBN, int quantity);

	public List<Book> removeBookFromShppingCart(String username, int bookISBN);
	
	public void checkoutShoppingCart(String username);

	public List<Book> getBookSalesPreviousMonth();
	
	public List<User> getTop5Users();
	
	public List<Book> getUserPurchases(String username);
	
	public List<Book> getTop10SoldBooks();
	
	public void close();
}
