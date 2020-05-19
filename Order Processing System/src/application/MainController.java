package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Authenticator;
import application.model.Book;
import application.model.DriverImp;
import application.model.Publisher;
import application.model.User;
import application.model.Validator;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {
	
	private User user;
	private DriverImp driver;
	private Validator validator = new Validator();
	private Validator v = new Validator();
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.CONFIRMATION);
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private TextField firstname;
	@FXML private TextField lastname;
	@FXML private TextField email;
	@FXML private TextField phone;
	@FXML private TextField shippingAddress;
	
	@FXML private Label errorsArea;
	@FXML private TextField lusername;
	@FXML private PasswordField lpassword;
	
	
	
	
	
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
	
	public void createUser(ActionEvent event) throws IOException, SQLException {
		
		driver = (DriverImp) DriverImp.getInstance();
		
		String usernameVal = username.getText();
		String passwordVal = password.getText();
		String firstnameVal = firstname.getText();
		String lastnameVal = lastname.getText();
		String emailVal = email.getText();
		String phoneVal = phone.getText();
		String shippingAddressVal = shippingAddress.getText();
		
		user = new User(usernameVal, emailVal, passwordVal, lastnameVal, firstnameVal, phoneVal, shippingAddressVal);
		
		String errors = validator.newUser(user);
		if(errors.equals("")) {
			try {
				driver.addNewUser(user);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				alert.setHeaderText("The account can't be created!");
				alert.setContentText(errors);
				alert.showAndWait();
				return;
			}
			alert_success.setHeaderText("The account was created successfully!");
			alert_success.showAndWait();
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
		else {
			alert.setHeaderText("The account can't be created, please follow these instructions!");
			alert.setContentText(errors);
			alert.showAndWait();
		}	
	}
	
	public void startShopping(ActionEvent event) {
		String usernameVal = lusername.getText();
		String passwordVal = lpassword.getText();
		
		try {
			driver = (DriverImp) DriverImp.getInstance();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			alert.setContentText(e2.getLocalizedMessage());
			alert.showAndWait();
		}
		try {
			driver.clearShoppingCart(usernameVal);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			user = driver.getUser(usernameVal, passwordVal);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			alert.setContentText(e1.getLocalizedMessage());
			alert.showAndWait();
			return;
		}
		if(user == null) {
			alert.setHeaderText("You have not an account, you need to sign-up first!");
			alert.showAndWait();
			return;
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

}
