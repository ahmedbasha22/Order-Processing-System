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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class ManageCustomersController implements Initializable {
	
	private DriverImp driver;
	private User user;
	
	@FXML private TableView<User> tableView;
	@FXML private TableColumn<User, String> username;
	@FXML private TableColumn<User, String> email;
	@FXML private TableColumn<Book, CheckBox> selected;
	
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.CONFIRMATION);

	public void initData(User user) {
		this.user = user;
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
		
		username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		selected.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("selected"));
		
		try {
			tableView.setItems(getUsers());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ObservableList<User> getUsers() throws SQLException{
		ObservableList<User> users = FXCollections.observableArrayList();
		List<User> u = driver.getAllUsers();
		users.addAll(u);
		return users;
	}
	
	public void continueShopping(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ShopArea.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
			
		ShopAreaController controller = loader.getController();
		controller.initData(user);
	}
}
