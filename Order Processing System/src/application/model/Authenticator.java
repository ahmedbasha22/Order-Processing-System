package application.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Authenticator {
	private Driver driver;
	public Authenticator() throws SQLException {
		driver = DriverImp.getInstance();
	}

	public List<String> newUser(User user) {
		List<String> errors = new ArrayList<String>();
		try {
			if (driver.alreadyRegistredUsername(user.getUsername())) {
				errors.add("Username is already registered");
			}
			if (driver.alreadyRegistredEmail(user.getEmail())){
				errors.add("Email is already registered");
			}
		} catch (SQLException e) {
			errors.add("An unexpected error occurred in database");
		}
		return errors;
	}
	
	public List<String> alreadyRegistredUsername(String username) {
		List<String> errors = new ArrayList<String>();
		try {
			if (driver.alreadyRegistredUsername(username)) {
				errors.add("Username is already registered");
			}
		} catch (SQLException e) {
			errors.add("An unexpected error occurred in database");
		}
		return errors;
	}

	public List<String> alreadyRegistredEmail(String email) {
		List<String> errors = new ArrayList<String>();
		try {
			if (driver.alreadyRegistredEmail(email)) {
				errors.add("Email is already registered");
			}
		} catch (SQLException e) {
			errors.add("An unexpected error occurred in database");
		}
		return errors;
	}
	
	public List<String> signIn(String username, String password) {
		List<String> errors = new ArrayList<String>();
		try {
			if (! driver.authenticateUser(username, password)) {
				errors.add("The username or password is incorrect");
			}
		} catch (SQLException e) {
			errors.add("An unexpected error occurred in database");
		}
		return errors;
	}
	
}
