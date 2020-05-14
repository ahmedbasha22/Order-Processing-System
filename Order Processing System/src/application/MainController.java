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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {

	
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

	
	
}
