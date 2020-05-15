package application.model;

import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private SimpleIntegerProperty ISBN;
	private SimpleStringProperty title;
	private List<SimpleStringProperty> authors;
	private SimpleDoubleProperty sellingPrice;
	private SimpleStringProperty category;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty publicationYear;
	private SimpleStringProperty publisherName;

	
	public Book(int ISBN, String title, double sellingPrice, String category, int quantity,
			int publicationYear, String publisherName) {
		this.ISBN = new SimpleIntegerProperty(ISBN);
		this.title = new SimpleStringProperty(title);
		this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
		this.category = new SimpleStringProperty(category);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.publicationYear = new SimpleIntegerProperty(publicationYear);
		this.publisherName = new SimpleStringProperty(publisherName);
	}

	public int getISBN() {
		return ISBN.get();
	}



	public void setISBN(SimpleIntegerProperty iSBN) {
		ISBN = iSBN;
	}



	public String getTitle() {
		return title.get();
	}



	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}



	public List<SimpleStringProperty> getAuthors() {
		return authors;
	}



	public void setAuthors(List<SimpleStringProperty> authors) {
		this.authors = authors;
	}


	public double getSellingPrice() {
		return sellingPrice.get();
	}



	public void setSellingPrice(SimpleDoubleProperty sellingPrice) {
		this.sellingPrice = sellingPrice;
	}



	public String getCategory() {
		return category.get();
	}



	public void setCategory(SimpleStringProperty category) {
		this.category = category;
	}



	public int getQuantity() {
		return quantity.get();
	}



	public void setQuantity(SimpleIntegerProperty quantity) {
		this.quantity = quantity;
	}



	public int getPublicationYear() {
		return publicationYear.get();
	}



	public void setPublicationYear(SimpleIntegerProperty publicationYear) {
		this.publicationYear = publicationYear;
	}



	public String getPublisherName() {
		return publisherName.get();
	
	}



	public void setPublisherName(SimpleStringProperty publisherName) {
		this.publisherName = publisherName;
	}



}
