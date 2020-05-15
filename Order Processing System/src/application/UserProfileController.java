package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserProfileController {
	
	private User user;
	
	@FXML private Label success;
	
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
	
	
	public void initData(User user) {
		this.user = user;
		usernameEdit.setText(user.getUsername());
		firstnameEdit.setText(user.getFirstName());
		lastnameEdit.setText(user.getLastName());
		emailEdit.setText(user.getEmail());
		phoneEdit.setText(user.getPhone());
		shippingAddressEdit.setText(user.getShppingAddress());
	}
	
	public void eUsername(ActionEvent event) {
		user.setUsername(usernameEdit.getText());
		usernameEdit.setText(user.getUsername());
		success.setText("- Username is updated successfully!");
	}
	
	public void eFirstname(ActionEvent event) {
		user.setFirstName(firstnameEdit.getText());
		success.setText("- First name is updated successfully!");
	}
	
	public void eLastname(ActionEvent event) {
		user.setLastName(lastnameEdit.getText());
		success.setText("- Last name is updated successfully!");
	}

	public void eEmail(ActionEvent event) {
		user.setEmail(emailEdit.getText());
		success.setText("- e-mail address is updated successfully!");
	}
	
	public void ePassword(ActionEvent event) {
		user.setPassword(passwordEdit.getText());
		success.setText("- Password is updated successfully!");
	}
	
	public void ePhone(ActionEvent event) {
		user.setPhone(phoneEdit.getText());
		success.setText("- Phone number is updated successfully!");
	}
	
	public void eShippingAddress(ActionEvent event) {
		user.setShppingAddress(shippingAddressEdit.getText());
		success.setText("- Shipping address is updated successfully!");
	}

}
