package application;

import java.io.IOException;
import java.sql.SQLException;

import application.model.DriverImp;
import application.model.User;
import application.model.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserProfileController {
	
	private User user;
	private DriverImp driver;
	
	@FXML private Label success;
	@FXML private Hyperlink m;
	
	@FXML private Button editUsername;
	@FXML private Button editFirstname;
	@FXML private Button editLastname;
	@FXML private Button editEmail;
	@FXML private Button editPassword;
	@FXML private Button editShippingAddress;
	@FXML private Button editPhone;
	@FXML private Button vCart;
	@FXML private Button bShop;
	
	@FXML private TextField usernameEdit;
	@FXML private PasswordField passwordEdit;
	@FXML private TextField firstnameEdit;
	@FXML private TextField lastnameEdit;
	@FXML private TextField emailEdit;
	@FXML private TextField phoneEdit;
	@FXML private TextField shippingAddressEdit;
	
	private Validator v = new Validator();
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.INFORMATION);
	
	
	public void initData(User user) throws SQLException {
		driver = (DriverImp) DriverImp.getInstance();
		this.user = user;
		usernameEdit.setText(user.getUsername());
		firstnameEdit.setText(user.getFirstName());
		lastnameEdit.setText(user.getLastName());
		emailEdit.setText(user.getEmail());
		phoneEdit.setText(user.getPhone());
		shippingAddressEdit.setText(user.getShppingAddress());
		if(!user.isManager()) m.setText("");
	}
	
	public void edit(ActionEvent event) {
		User temp = new User(usernameEdit.getText(), emailEdit.getText(),
								passwordEdit.getText(), lastnameEdit.getText(), firstnameEdit.getText(),
									phoneEdit.getText(), shippingAddressEdit.getText());
		String errors = v.newUser(temp);
		if (!errors.equals("")) {
			alert.setHeaderText("Your information can't be updated!");
			alert.setContentText(errors);
			alert.showAndWait();
		}
		else {
			try {
				user = driver.modifyExistingUser(user.getUsername(), user.getPassword(), temp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				alert.setHeaderText("Your information can't be updated!");
				alert.setContentText(e.getLocalizedMessage());
				alert.showAndWait();
				return;
			}
			alert_success.setHeaderText("Your information is updated successfully!");
			alert_success.showAndWait();
		}
		
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
	public void viewCart(ActionEvent event) {
		try {
			driver = (DriverImp) DriverImp.getInstance();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
		}
		
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
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
		CartController controller = loader.getController();
		controller.initData(user);
	}
	
	public void viewManagement(ActionEvent event) throws SQLException {
		try {
			driver = (DriverImp) DriverImp.getInstance();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
		}
		
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("Management.fxml"));
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
		ManagementController controller = loader.getController();
		controller.initData(user);
	}

}
