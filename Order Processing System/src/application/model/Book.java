package application.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Book {
	private SimpleStringProperty ISBN;
	private SimpleStringProperty title;
	private List<SimpleStringProperty> authors;
	private SimpleStringProperty sellingPrice;
	private SimpleStringProperty category;
	private SimpleStringProperty quantity;
	private SimpleStringProperty publicationYear;
	private final StringProperty publisherName;
	private SimpleStringProperty minQuantity;
	private CheckBox selected;
	private CheckBox selectedO;
	private SimpleStringProperty addedQ;
	private SimpleStringProperty finalQuantity;
	
	public Book(String ISBN, String title, String publicationYear,  String sellingPrice, 
			String category, String quantity, String publisherName, List<String> authors, String minQ) {
		this.ISBN = new SimpleStringProperty(ISBN);
		this.title = new SimpleStringProperty(title);
		this.sellingPrice = new SimpleStringProperty(sellingPrice);
		this.category = new SimpleStringProperty(category);
		this.quantity = new SimpleStringProperty(quantity);
		this.publicationYear = new SimpleStringProperty(publicationYear);
		this.publisherName = new SimpleStringProperty(publisherName);
		this.minQuantity = new SimpleStringProperty(minQ);
		this.authors = new ArrayList<SimpleStringProperty>();
		for(String author : authors) {
			this.authors.add(new SimpleStringProperty(author));
		}
		this.selected = new CheckBox();
		this.selectedO = new CheckBox();
		this.addedQ = new SimpleStringProperty("0");
		finalQuantity = new SimpleStringProperty("0");
	}

	public String getISBN() {
		return ISBN.get();
	}



	public void setISBN(String iSBN) {
		ISBN.set(iSBN);
	}



	public String getTitle() {
		return title.get();
	}



	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getFinalQuantity() {
		return finalQuantity.get();
	}

	public void setFinalQuantity(String f) {
		this.finalQuantity.set(f);
	}

	public List<String> getAuthors() {
		List<String> result = new ArrayList<String>();
		for(SimpleStringProperty a : authors) {
			result.add(a.get());
		}
		return result;
	}



	public void setAuthors(List<SimpleStringProperty> authors) {
		this.authors = authors;
	}


	public String getSellingPrice() {
		return sellingPrice.get();
	}



	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice.set(sellingPrice);;
	}



	public String getCategory() {
		return category.get();
	}



	public void setCategory(String category) {
		this.category.set(category);
	}



	public String getQuantity() {
		return quantity.get();
	}



	public void setQuantity(String quantity) {
		this.quantity.set(quantity);
	}



	public String getPublicationYear() {
		return publicationYear.get();
	}



	public void setPublicationYear(String publicationYear) {
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

		public String getMinQuantity() {
			return minQuantity.get();
		}



		public void setMinQuantity(String minQ) {
			this.minQuantity.set(minQ);
		}

	public CheckBox getSelected() {
		return this.selected;
	}
	
	public void setSelected(CheckBox cb) {
		this.selected = cb;
	}
	
	public CheckBox getSelectedO() {
		return this.selectedO;
	}
	
	public void setSelectedO(CheckBox cb) {
		this.selectedO = cb;
	}

	public void setAddedQ(String s) {
		addedQ.set(s);
	}
	
	public String getAddedQ() {
		return addedQ.get();
	}
}
