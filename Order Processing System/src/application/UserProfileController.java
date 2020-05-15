package application;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserProfileController implements Initializable {
	
	private User user;
	
	@FXML private Button editUsername;
	@FXML private Button editFirstname;
	@FXML private Button editLastname;
	@FXML private Button editEmail;
	@FXML private Button editPassword;
	@FXML private Button editShippingAddress;
	@FXML private Button editPhone;
	
	@FXML private TextField usernameEdit;
	@FXML private PasswordField passwordEdit;
	@FXML private TextField firstnameEdit;
	@FXML private TextField lastnameEdit;
	@FXML private TextField emailEdit;
	@FXML private TextField phoneEdit;
	@FXML private TextField shippingAddressEdit;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usernameEdit.setText(user.getUsername());
		firstnameEdit.setText(user.getFirstName());
		lastnameEdit.setText(user.getLastName());
		emailEdit.setText(user.getEmail());
		phoneEdit.setText(user.getPhone());
		shippingAddressEdit.setText(user.getShppingAddress());
	}

}
