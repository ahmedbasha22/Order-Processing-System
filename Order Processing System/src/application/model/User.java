package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class User {
	private SimpleStringProperty username;
	private SimpleStringProperty email;
	private String password;
	private String lastName;
	private String firstName;
	private String phone;
	private String shppingAddress;
	private boolean isManager;
	private CheckBox selected;
	
	public CheckBox getSelected() {
		return selected;
	}

	public void setSelected(CheckBox selected) {
		this.selected = selected;
	}

	public User(String username, String email, String password, String lastName, String firstName, String phone,
			String shppingAddress) {
		super();
		this.username = new SimpleStringProperty(username);
		this.email = new SimpleStringProperty(email);
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phone = phone;
		this.shppingAddress = shppingAddress;
		this.isManager = false;
		this.selected = new CheckBox();
	}
	
	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public String getUsername() {
		return username.get();
	}
	public void setUsername(String username) {
		this.username.set(username);
	}
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShppingAddress() {
		return shppingAddress;
	}
	public void setShppingAddress(String shppingAddress) {
		this.shppingAddress = shppingAddress;
	}	
}
