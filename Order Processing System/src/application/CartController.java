package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Book;
import application.model.DriverImp;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class CartController implements Initializable {
	
	private DriverImp driver;
	
	private User user;
	
	@FXML private Label successAdd;
	@FXML private Label costLabel;
	
	@FXML private TableView<Book> tableView;
	@FXML private TableColumn<Book, String> isbn;
	@FXML private TableColumn<Book, String> title;
	@FXML private TableColumn<Book, List<String>> authors;
	@FXML private TableColumn<Book, String> publisherName;
	@FXML private TableColumn<Book, String> publicationYear;
	@FXML private TableColumn<Book, String> sellingPrice;
	@FXML private TableColumn<Book, String> category;	
	@FXML private TableColumn<Book, String> finalQuantity;
	@FXML private TableColumn<Book, CheckBox> selected;
	
	
	@FXML private Hyperlink viewP;
	
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.CONFIRMATION);
	
	public void initData(User user) {
		this.user = user;
		try {
			tableView.setItems(getBooks());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		computeCost();
	}
	
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
		finalQuantity.setCellValueFactory(new PropertyValueFactory<Book, String>("quantity"));
		authors.setCellValueFactory(new PropertyValueFactory<Book, List<String>>("authors"));	
		selected.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("selected"));
		
		tableView.setEditable(true);
		finalQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public ObservableList<Book> getBooks() throws SQLException{
		ObservableList<Book> books = FXCollections.observableArrayList();
		List<Book> b = null;
		try {
			b = driver.getShoppingCart(user.getUsername());
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
		books.addAll(b);
		return books;
	}
	
	public void viewProfile(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("UserProfile.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
			
		UserProfileController controller = loader.getController();
		controller.initData(user);
	}
	
	public void removeFromChart(ActionEvent event) throws SQLException {
		String successM = "";
		alert_success.setHeaderText("These books were removed cart successfully!");
		ObservableList<Book> books = tableView.getItems();
		ObservableList<Book> selectedBooks = FXCollections.observableArrayList();
		for(Book sb : books) {
			if(sb.getSelected().isSelected()) {
				if((Integer.parseInt(sb.getQuantity()) - Integer.parseInt(sb.getAddedQ())) < 0) {
					alert.setHeaderText("Please insert correct quantity to remove!");
					alert.showAndWait();
					return;
				}
				else if ((Integer.parseInt(sb.getQuantity()) - Integer.parseInt(sb.getAddedQ())) == 0){
					selectedBooks.add(sb);
					try {
						driver.removeBookFromShoppingCart(user.getUsername(), Integer.parseInt(sb.getISBN()));
						Book t = driver.getBooksByISBN(Integer.parseInt(sb.getISBN())).get(0);
						t.setQuantity(Integer.toString((Integer.parseInt(t.getQuantity()) + Integer.parseInt(sb.getAddedQ()))));
						driver.modifyBookQuantity(Integer.parseInt(sb.getISBN()), Integer.parseInt(t.getQuantity()));
						successM += sb.getAddedQ() + " of " + sb.getTitle() + " book, No more of this book in the cart!\n";
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						alert.setHeaderText(e1.getLocalizedMessage());
						alert.showAndWait();
						return;
					}
				}
				else {
					try {
					//	driver.addBookToShoppingCart(user.getUsername(), Integer.parseInt(sb.getISBN()), Integer.parseInt(sb.getAddedQ()));
						sb.setQuantity(Integer.toString((Integer.parseInt(sb.getQuantity()) - Integer.parseInt(sb.getAddedQ()))));
						Book t = driver.getBooksByISBN(Integer.parseInt(sb.getISBN())).get(0);
						t.setQuantity(Integer.toString((Integer.parseInt(t.getQuantity()) + Integer.parseInt(sb.getAddedQ()))));
						driver.modifyBookQuantity(Integer.parseInt(sb.getISBN()), Integer.parseInt(t.getQuantity()));
						successM += sb.getAddedQ() + " of " + sb.getTitle() + " book\n";
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						successAdd.setText(e1.getMessage());
					}
				}
			}
		}
		alert_success.setContentText(successM);
		alert_success.showAndWait();
		books.removeAll(selectedBooks);
		tableView.setItems(books);
		computeCost();
	}
	
	public void updateQuantity(CellEditEvent editedCell) throws NumberFormatException, SQLException {
		ObservableList<Book> books = tableView.getItems();
		ObservableList<Book> newBooks = FXCollections.observableArrayList();
		String removedQuantity =  editedCell.getNewValue().toString();
		Book selectedBook = tableView.getSelectionModel().getSelectedItem();
		selectedBook.setAddedQ(removedQuantity);
		for(Book b : books) {
			if(b.getISBN() == selectedBook.getISBN()) newBooks.add(selectedBook);
			else newBooks.add(b);
		}
		tableView.setItems(newBooks);
	}
	
	public void computeCost() {
		ObservableList<Book> books = tableView.getItems();
		double cost = 0;
		if(books != null) {
			for(Book b : books) {
				cost += Double.parseDouble(b.getSellingPrice()) * Double.parseDouble(b.getQuantity());
			}
		}
		costLabel.setText(Double.toString(cost) + " $");
	}
	
	public void checkout(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Checkout.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
			
		CheckoutController controller = loader.getController();
		controller.initData(user);
	}

}
