package application;

import java.io.IOException;
import java.sql.SQLException;

import application.model.DriverImp;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagementController {
	
	private User user;
	DriverImp driver;
	
	public void initData(User user) throws SQLException {
		this.user = user;
		driver = (DriverImp) DriverImp.getInstance();
	}
	
	public void viewBooksWarehouse(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("BooksWarehouse.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
		
	}
	
	public void viewManageCustomers(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ManageCustomers.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
		
		ManageCustomersController controller = loader.getController();
		controller.initData(user);
	}
	public void viewManageOrders(ActionEvent event) {
	
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

}
