package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.Driver;

import application.model.Book;
import application.model.DriverImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TableController implements Initializable {
	
	private DriverImp driver;
	
	@FXML private Label successAdd;
	
	@FXML private TableView<Book> tableView;
	@FXML private TableColumn<Book, String> isbn;
	@FXML private TableColumn<Book, String> title;
	@FXML private TableColumn<Book, List<String>> authors;
	@FXML private TableColumn<Book, String> publisherName;
	@FXML private TableColumn<Book, Integer> publicationYear;
	@FXML private TableColumn<Book, Double> sellingPrice;
	@FXML private TableColumn<Book, String> category;	
	@FXML private TableColumn<Book, Integer> quantity;
	@FXML private TableColumn<Book, Integer> minQuantity;
	
	@FXML private TextField isbnTextField;
	@FXML private TextField titleTextField;
	@FXML private TextField authorsTextField;
	@FXML private TextField publisherNameTextField;
	@FXML private TextField publicationYearTextField;
	@FXML private TextField sellingPriceTextField;
	@FXML private TextField categoryTextField;
	@FXML private TextField quantityTextField;
	@FXML private TextField minQuantityTextField;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			driver = (DriverImp) DriverImp.getInstance();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
		publicationYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("publicationYear"));
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("sellingPrice"));
		category.setCellValueFactory(new PropertyValueFactory<Book, String>("Category"));
		quantity.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Quantity"));
		publisherName.setCellValueFactory(new PropertyValueFactory<Book, String>("publisherName"));
		authors.setCellValueFactory(new PropertyValueFactory<Book, List<String>>("authors"));
		minQuantity.setCellValueFactory(new PropertyValueFactory<Book, Integer>("minQuantity"));
		
		try {
			tableView.setItems(getBooks());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableView.setEditable(true);
		isbn.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public ObservableList<Book> getBooks() throws SQLException{
		ObservableList<Book> books = FXCollections.observableArrayList();
		List<Book> b = driver.getAllBooks();
		books.addAll(b);
		return books;
	}

	
	public void newBookButtonPushed() throws SQLException {
		String isbnValue = isbnTextField.getText();
		String titleValue = titleTextField.getText();
		double sellingPriceValue = Double.parseDouble(sellingPriceTextField.getText());
		String categoryValue = categoryTextField.getText();
		int quantityValue = Integer.parseInt(quantityTextField.getText());
		int publicationValue = Integer.parseInt(publicationYearTextField.getText());
		String publisherNameValue = publisherNameTextField.getText();
		int minQValue = Integer.parseInt(minQuantityTextField.getText());
		String[] authors = authorsTextField.getText().split(",");
		List<String> authorsValue = new ArrayList<String>(Arrays.asList(authors));
		
		Book book = new Book(isbnValue, titleValue, publicationValue, sellingPriceValue,
				categoryValue, quantityValue, publisherNameValue, authorsValue, minQValue);
		
		//Add the book to the database
		driver.addNewBook(book, minQValue);
		successAdd.setText("- The book is added successfully!");
		tableView.getItems().add(book);
		
	}
	
	public void editISBN(CellEditEvent editedCell) {
		String newISBN =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		
		//driver.modifyBookISBN(InteeditedCell.getOldValue(), newISBN);
		newBook.setISBN(newISBN);
	}
	
	
	
	
	
	
	
	
	
}
