package application.model;

import java.util.List;

public class Book {
	private int ISBN;
	private String title;
	private List<String> authors;
	private double sellingPrice;
	private String category;
	private int quantity;
	private int publicationYear;
	private String publisherName;
	
	public Book(int ISBN, String title, List<String> authors, double sellingPrice, String category, int quantity,
			int publicationYear, String publisherName) {
		super();
		this.ISBN = ISBN;
		this.title = title;
		this.authors = authors;
		this.sellingPrice = sellingPrice;
		this.category = category;
		this.quantity = quantity;
		this.publicationYear = publicationYear;
		this.publisherName = publisherName;
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

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
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
	
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

}
