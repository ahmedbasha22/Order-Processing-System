package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Book;
import application.model.Publisher;
import application.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {
	
	User user;
	
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private TextField email;
	@FXML private TextField phone;
	@FXML private TextField shippingAddress;
	
	
	
	
	public void goSignup(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
			Parent root;

			try {
			    root = loader.load();
			} catch (IOException ioe) {
			    // log exception
			    return;
			}

			Scene scene = new Scene(root,775,513);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
			window.setScene(scene);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void goLogin(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			Parent root;

			try {
			    root = loader.load();
			} catch (IOException ioe) {
			    // log exception
			    return;
			}

			Scene scene = new Scene(root,775,513);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
			window.setScene(scene);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createUser(ActionEvent event) throws IOException {
		String usernameVal = username.getText();
		String passwordVal = password.getText();
		String firstnameVal = firstname.getText();
		String lastnameVal = lastname.getText();
		String emailVal = email.getText();
		String phoneVal = phone.getText();
		String shippingAddressVal = shippingAddress.getText();
		
		user = new User(usernameVal, emailVal, passwordVal, lastnameVal, firstnameVal, phoneVal, shippingAddressVal);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("UserProfile.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root,775,513);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
			
		
		UserProfileController controller = loader.getController();
		
		
	}
	

}
