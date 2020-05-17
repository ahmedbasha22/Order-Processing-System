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
import javafx.scene.control.TablePosition;
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
	@FXML private TableColumn<Book, String> publicationYear;
	@FXML private TableColumn<Book, String> sellingPrice;
	@FXML private TableColumn<Book, String> category;	
	@FXML private TableColumn<Book, String> quantity;
	@FXML private TableColumn<Book, String> minQuantity;
	
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
		publicationYear.setCellValueFactory(new PropertyValueFactory<Book, String>("publicationYear"));
		sellingPrice.setCellValueFactory(new PropertyValueFactory<Book, String>("sellingPrice"));
		category.setCellValueFactory(new PropertyValueFactory<Book, String>("Category"));
		quantity.setCellValueFactory(new PropertyValueFactory<Book, String>("Quantity"));
		publisherName.setCellValueFactory(new PropertyValueFactory<Book, String>("publisherName"));
		authors.setCellValueFactory(new PropertyValueFactory<Book, List<String>>("authors"));
		minQuantity.setCellValueFactory(new PropertyValueFactory<Book, String>("minQuantity"));
		
		try {
			tableView.setItems(getBooks());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableView.setEditable(true);
		isbn.setCellFactory(TextFieldTableCell.forTableColumn());
		title.setCellFactory(TextFieldTableCell.forTableColumn());
		publicationYear.setCellFactory(TextFieldTableCell.forTableColumn());
		sellingPrice.setCellFactory(TextFieldTableCell.forTableColumn());
		category.setCellFactory(TextFieldTableCell.forTableColumn());
		quantity.setCellFactory(TextFieldTableCell.forTableColumn());
		minQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
		publisherName.setCellFactory(TextFieldTableCell.forTableColumn());
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
		String sellingPriceValue = sellingPriceTextField.getText();
		String categoryValue = categoryTextField.getText();
		String quantityValue = quantityTextField.getText();
		String publicationValue = publicationYearTextField.getText();
		String publisherNameValue = publisherNameTextField.getText();
		String minQValue = minQuantityTextField.getText();
		String[] authors = authorsTextField.getText().split(",");
		List<String> authorsValue = new ArrayList<String>(Arrays.asList(authors));
		
		Book book = new Book(isbnValue, titleValue, publicationValue, sellingPriceValue,
				categoryValue, quantityValue, publisherNameValue, authorsValue, minQValue);
		
		//Add the book to the database
		driver.addNewBook(book);
		successAdd.setText("- The book is added successfully!");
		tableView.getItems().add(book);
		
	}
	
	public void editISBN(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newISBN =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookISBN(Integer.parseInt(editedCell.getOldValue().toString()), Integer.parseInt(newISBN));
		newBook.setISBN(newISBN);
		successAdd.setText("- ISBN is updated successfully!");
	}
	
	public void editTitle(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newTitle =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookTitle(Integer.parseInt(newBook.getISBN()), newTitle);
		newBook.setTitle(newTitle);
		successAdd.setText("- Title is updated successfully!");
	}
	
	public void editPublicationYear(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newPublicationYear =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookPublicationYear(Integer.parseInt(newBook.getISBN()), Integer.parseInt(newPublicationYear));
		newBook.setPublicationYear(newPublicationYear);
		successAdd.setText("- Publiction Year is updated successfully!");
	}
	
	public void editSellingPrice(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newSellingPrice =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookSellingPrice(Integer.parseInt(newBook.getISBN()), Double.parseDouble(newSellingPrice));
		newBook.setSellingPrice(newSellingPrice);
		successAdd.setText("- Selling Price is updated successfully!");
	}
	
	public void editCategory(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newCategory =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookCategory(Integer.parseInt(newBook.getISBN()), newCategory);
		newBook.setCategory(newCategory);
		successAdd.setText("- Category is updated successfully!");
	}
	
	public void editQuantity(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newQuantity =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookQuantity(Integer.parseInt(newBook.getISBN()), Integer.parseInt(newQuantity));
		newBook.setQuantity(newQuantity);
		successAdd.setText("- Quantity is updated successfully!");
	}
	
	public void editMinQuantity(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newMinQuantity =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookMinimumQuantity(Integer.parseInt(newBook.getISBN()), Integer.parseInt(newMinQuantity));
		newBook.setMinQuantity(newMinQuantity);
		successAdd.setText("- Min. Quantity is updated successfully!");
	}
	
	public void editPublisher(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		String newPublisher =  editedCell.getNewValue().toString();
		Book newBook = tableView.getSelectionModel().getSelectedItem();
		//Update Database
		driver.modifyBookPublisherName(Integer.parseInt(newBook.getISBN()), newPublisher);
		newBook.setPublisherName(newPublisher);
		successAdd.setText("- Publisher name is updated successfully!");
	}
	
	
	
	
	
	
	
	
	
	
	
}
