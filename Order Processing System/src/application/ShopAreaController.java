package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Book;
import application.model.DriverImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class ShopAreaController implements Initializable {
	
	private DriverImp driver;
	
	
	@FXML private Label successAdd;
	@FXML private Label successAdd1;
	
	@FXML private TableView<Book> tableView;
	@FXML private TableColumn<Book, String> isbn;
	@FXML private TableColumn<Book, String> title;
	@FXML private TableColumn<Book, List<String>> authors;
	@FXML private TableColumn<Book, String> publicationYear;
	@FXML private TableColumn<Book, String> sellingPrice;
	@FXML private TableColumn<Book, String> category;	
	@FXML private TableColumn<Book, CheckBox> selected;
	@FXML private TableColumn<Book, String> addedQuantity;
	
	@FXML private TextField searchISBN;
	@FXML private TextField searchTitle;
	@FXML private TextField searchPublicationYear;
	@FXML private TextField searchSellingPrice;
	@FXML private TextField searchCategory;
	@FXML private TextField searchAuthors;
	
	
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
		addedQuantity.setCellValueFactory(new PropertyValueFactory<Book, String>("addedQ"));
		authors.setCellValueFactory(new PropertyValueFactory<Book, List<String>>("authors"));
		selected.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("selected"));
		
		try {
			tableView.setItems(getBooks());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableView.setEditable(true);
		addedQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public ObservableList<Book> getBooks() throws SQLException{
		ObservableList<Book> books = FXCollections.observableArrayList();
		List<Book> b = driver.getAllBooks();
		books.addAll(b);
		return books;
	}
	
	public void addToChart(ActionEvent event) throws SQLException {
		ObservableList<Book> books = tableView.getItems();
		ObservableList<Book> selectedBooks = FXCollections.observableArrayList();
		boolean found = false;
		boolean printed = false;
		String a = "";
		for(Book sb : books) {
			if(sb.getSelected().isSelected()) {
				a += sb.getAddedQ();
				if((Integer.parseInt(sb.getQuantity()) - Integer.parseInt(sb.getAddedQ())) < Integer.parseInt(sb.getMinQuantity())) {
					found = true;
				}
				else if ((Integer.parseInt(sb.getQuantity()) - Integer.parseInt(sb.getAddedQ())) == Integer.parseInt(sb.getMinQuantity())){
					selectedBooks.add(sb);
					successAdd1.setText("Added "+ sb.getAddedQ() + " to the cart successfully!, No more of this book!");
					successAdd.setText("");
					printed = true;
				}
				else {
					sb.setQuantity(Integer.toString((Integer.parseInt(sb.getQuantity()) - Integer.parseInt(sb.getAddedQ()))));
				}
			}
		}
		if(found) {
			successAdd.setText("- There is not available quantity for this book!");
			successAdd1.setText("");
		}
		else if(!printed) {
			successAdd.setText("");
			successAdd1.setText("Added " + a + " to the cart successfully!");
		}
		books.removeAll(selectedBooks);
		tableView.setItems(books);
	}
	
	public void updateQuantity(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		ObservableList<Book> books = tableView.getItems();
		ObservableList<Book> newBooks = FXCollections.observableArrayList();
		String addedQuantity =  editedCell.getNewValue().toString();
		Book selectedBook = tableView.getSelectionModel().getSelectedItem();
		selectedBook.setAddedQ(addedQuantity);
		for(Book b : books) {
			if(b.getISBN() == selectedBook.getISBN()) newBooks.add(selectedBook);
			else newBooks.add(b);
		}
		tableView.setItems(newBooks);
	}
	
	public void search(ActionEvent event) throws NumberFormatException, SQLException {
		ObservableList<Book> newBooks = FXCollections.observableArrayList();
		String isbn = searchISBN.getText();
		String title = searchTitle.getText();
		String publicationYear = searchPublicationYear.getText();
		String sellingPrice = searchSellingPrice.getText();
		String category = searchCategory.getText();
		String author = searchAuthors.getText();
		if(!isbn.equals("")) {
			List<Book> matchedBooks = driver.getBooksByISBN(Integer.parseInt(isbn));
			newBooks.addAll(matchedBooks);
		}
		else if(!title.equals("")) {
			List<Book> matchedBooks = driver.getBooksByTitle(title);
			newBooks.addAll(matchedBooks);
		}
		else if(!publicationYear.equals("")) {
			List<Book> matchedBooks = driver.getBooksByPublicationYear(Integer.parseInt(publicationYear));
			newBooks.addAll(matchedBooks);
		}
		else if(!sellingPrice.equals("")) {
			List<Book> matchedBooks = driver.getBooksBySellingPrice(Double.parseDouble(sellingPrice));
			newBooks.addAll(matchedBooks);
		}
		else if(!category.equals("")) {
			List<Book> matchedBooks = driver.getBooksByCatgory(category);
			newBooks.addAll(matchedBooks);
		}
		else if(!author.equals("")) {
			List<Book> matchedBooks = driver.getBooksByAuthor(author);
			newBooks.addAll(matchedBooks);
		}
		else {
			newBooks.addAll(driver.getAllBooks());
		}
		tableView.setItems(newBooks);
	}
}
	
	

