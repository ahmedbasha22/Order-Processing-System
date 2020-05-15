package application.model;

import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
	private SimpleIntegerProperty ISBN;
	private SimpleStringProperty title;
	private List<SimpleStringProperty> authors;
	private SimpleDoubleProperty sellingPrice;
	private SimpleStringProperty category;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty publicationYear;
	private final StringProperty publisherName;

	
	public Book(int ISBN, String title, int publicationYear,  double sellingPrice, 
			String category, int quantity, String publisherName, List<String> authors) {
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



	public void setISBN(int iSBN) {
		ISBN.set(iSBN);
	}



	public String getTitle() {
		return title.get();
	}



	public void setTitle(String title) {
		this.title.set(title);
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



	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice.set(sellingPrice);;
	}



	public String getCategory() {
		return category.get();
	}



	public void setCategory(String category) {
		this.category.set(category);
	}



	public int getQuantity() {
		return quantity.get();
	}



	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}



	public int getPublicationYear() {
		return publicationYear.get();
	}



	public void setPublicationYear(int publicationYear) {
		this.publicationYear.set(publicationYear);
	}



	public final StringProperty publisherNameProperty() {
		   return publisherName;
		}

		public final String getPublisherName() {
		   return publisherName.get();
		}

		public final void setPublisherName(String value) {
			publisherName.set(value);
		}



}
