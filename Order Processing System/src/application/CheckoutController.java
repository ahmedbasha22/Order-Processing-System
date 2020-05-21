package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.mysql.cj.jdbc.Driver;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckoutController {

	private User user;
	private Validator v;
	DriverImp driver;
	
	@FXML private TextField n;
	@FXML private DatePicker e;
	
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.INFORMATION);
	
	public void initData(User user) {
		this.user = user;
		v = new Validator();
		try {
			driver = (DriverImp) DriverImp.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void order(ActionEvent event) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Long creditNumber = Long.parseLong(n.getText());
		LocalDate exDate = e.getValue();
		Date date = Date.from(exDate.atStartOfDay(defaultZoneId).toInstant());
		if(v.isValidCreditCard(creditNumber, date)) {
			try {
				driver.checkoutShoppingCart(user.getUsername());
				alert_success.setHeaderText("The order was made successfully!");
				alert_success.showAndWait();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				alert.setHeaderText("The order can't be made!");
				alert.setContentText(e1.getLocalizedMessage());
				alert.showAndWait();
			}
		}
		else {
			alert.setHeaderText("The order can't be made!");
			alert.setContentText("Please provide a correct credit card.");
			alert.showAndWait();
		}	
	}
}
