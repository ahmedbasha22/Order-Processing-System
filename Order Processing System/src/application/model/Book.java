package application.model;

import java.util.List;

public class Book {
	private int ISBN;
	private String title;
	private List<String> authors;
	private int sellingPrice;
	private String category;
	private int quantity;
	private int publicationYear;
	private Publisher publisher;
	
	public Book(int ISBN, String title, List<String> authors, int sellingPrice, String category, int quantity,
			int publicationYear, Publisher publisher) {
		super();
		this.ISBN = ISBN;
		this.title = title;
		this.authors = authors;
		this.sellingPrice = sellingPrice;
		this.category = category;
		this.quantity = quantity;
		this.publicationYear = publicationYear;
		this.publisher = publisher;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}
