package application;

import java.io.IOException;
import java.sql.SQLException;

import application.jasper.JasperReports;
import application.model.DriverImp;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManagementController {
	
	private User user;
	private JasperReports jasper;
	DriverImp driver;
	
	Alert alert = new Alert(AlertType.ERROR);
	Alert alert_success = new Alert(AlertType.INFORMATION);
	
	public void initData(User user) throws SQLException {
		this.user = user;
		driver = (DriverImp) DriverImp.getInstance();
		jasper = new JasperReports();
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
	public void viewManageOrders(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ManageOrders.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
		window.setScene(scene);
		window.show();
			
		ConfirmOrdersController controller = loader.getController();
		controller.initData(user);
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
	
	public void getBookSalesPreviousMonth(ActionEvent event) {
		try {
			jasper.getBookSalesPreviousMonth();
			alert_success.setHeaderText("The report was created successfully!");
			alert_success.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			alert.setHeaderText("The report can't be created!");
			alert.setContentText(e.getLocalizedMessage());
			alert.showAndWait();
		}
	}
	
	public void getTop5Users() {
		try {
			jasper.getTop5Users();
			alert_success.setHeaderText("The report was created successfully!");
			alert_success.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			alert.setHeaderText("The report can't be created!");
			alert.setContentText(e.getLocalizedMessage());
			alert.showAndWait();
		}
	}
	
	public void getTop10SoldBooks() {
		try {
			jasper.getTop10SoldBooks();
			alert_success.setHeaderText("The report was created successfully!");
			alert_success.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			alert.setHeaderText("The report can't be created!");
			alert.setContentText(e.getLocalizedMessage());
			alert.showAndWait();
		}
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

}
