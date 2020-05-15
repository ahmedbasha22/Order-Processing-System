package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Book;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableController implements Initializable {
	@FXML private TableView<Book> tableView;
	@FXML private TableColumn<Book, Integer> isbn;
	@FXML private TableColumn<Book, String> title;
	@FXML private TableColumn<Book, List<String>> authors;
	@FXML private TableColumn<Book, String> publisher;
	@FXML private TableColumn<Book, Integer> publicationYear;
	@FXML private TableColumn<Book, Double> sellingPrice;
	@FXML private TableColumn<Book, String> category;	
	@FXML private TableColumn<Book, Integer> quantity;
	
	@FXML private TextField isbnTextField;
	@FXML private TextField titleTextField;
	@FXML private TextField authorsTextField;
	@FXML private TextField publisherTextField;
	@FXML private TextField publicationYearTextField;
	@FXML private TextField sellingPriceTextField;
	@FXML private TextField categoryTextField;
	@FXML private TextField quantityTextField;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		isbn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("ISBN"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
		authors.setCellValueFactory(new PropertyValueFactory<Book, List<String>>("Author(s)"));
		publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("Publisher"));
		publicationYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Publication Year"));
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("Selling Price"));
		category.setCellValueFactory(new PropertyValueFactory<Book, String>("Category"));
		quantity.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Quantity"));
		
		tableView.setItems(getBooks());
		
	}
	
	public ObservableList<Book> getBooks(){
		ObservableList<Book> books = FXCollections.observableArrayList();
		books.add(new Book(1, "s", 5.0, "a", 100, 1998, "a"));
		return books;
	}

	
	public void newBookButtonPushed() {
		int isbnValue = Integer.parseInt(isbnTextField.getText());
		String titleValue = titleTextField.getText();
		double sellingPriceValue = Double.parseDouble(sellingPriceTextField.getText());
		String categoryValue = categoryTextField.getText();
		int quantityValue = Integer.parseInt(quantityTextField.getText());
		int publicationValue = Integer.parseInt(publicationYearTextField.getText());
		String publisherValue = publisherTextField.getText();
		
		
		
		Book book = new Book(isbnValue, titleValue, sellingPriceValue,
				categoryValue, quantityValue, publicationValue, publisherValue);
		
		tableView.getItems().add(book);
		
	}
	
	
	
	
	
	
	
	
	
	
}
