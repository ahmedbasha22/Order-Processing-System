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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ConfirmOrdersController implements Initializable {
	
	private User user;
	DriverImp driver;
	
	@FXML private TableView<Book> tableView;
	@FXML private TableColumn<Book, String> isbn;
	@FXML private TableColumn<Book, String> quantity;	
	@FXML private TableColumn<Book, CheckBox> selectedO;
	
	
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.INFORMATION);
	
	public void initData(User user) {
		this.user = user;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN"));
		quantity.setCellValueFactory(new PropertyValueFactory<Book, String>("quantity"));
		selectedO.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("selectedO"));
		try {
			driver = (DriverImp) DriverImp.getInstance();
			tableView.setItems(getOrders());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());;
		}
	}
	
	public ObservableList<Book> getOrders() throws SQLException{
		ObservableList<Book> books = FXCollections.observableArrayList();
		List<Book> b = driver.getOrderedBooks();
		books.addAll(b);
		return books;
	}
	
	public void continueShopping(ActionEvent event) {
		try {
			driver = (DriverImp) DriverImp.getInstance();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
		}
		
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("ShopArea.fxml"));
			Parent root;

			try {
			    root = loader.load();
			} catch (IOException ioe) {
			    // log exception
			    return;
			}

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
			window.setScene(scene);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		ShopAreaController controller = loader.getController();
		controller.initData(user);
	}
	
	public void logout(ActionEvent event) throws IOException {
		try {
			driver.clearShoppingCart(user.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			alert.setHeaderText(e.getLocalizedMessage());
			alert.showAndWait();
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Sample.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
	}
	
	public void viewCart(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Cart.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
			
		CartController controller = loader.getController();
		controller.initData(user);
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
	
	public void order(ActionEvent event) {
		String successM = "";
		alert_success.setHeaderText("These orders were confirmed successfully!");
		ObservableList<Book> books = tableView.getItems();
		ObservableList<Book> selectedBooks = FXCollections.observableArrayList();
		for(Book sb : books) {
			if(sb.getSelectedO().isSelected()) {
				try {
					driver.confirmOrder(Integer.parseInt(sb.getISBN()));
					driver.orderMoreQuantity(Integer.parseInt(sb.getISBN()), Integer.parseInt(sb.getQuantity()));
					selectedBooks.add(sb);
					successM += ("Book of ISBN " + sb.getISBN());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					alert.setHeaderText(e.getLocalizedMessage());
					alert.showAndWait();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					alert.setHeaderText(e.getLocalizedMessage());
					alert.showAndWait();
				}
			}
		}
		alert_success.setContentText(successM);
		alert_success.showAndWait();
		books.removeAll(selectedBooks);
		tableView.setItems(books);		
	}
}
