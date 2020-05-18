package application.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	public String newUser(User user) {
		String errors = "";
		if (user.getUsername().length() < 8) {
			errors += "Username length is too short, it must be more than 8 characters\n";
		}
		if (user.getPassword().length() == 0) {
			errors  += "Please insert your password\n";
		}
		else if (user.getPassword().length() < 4) {
			errors  += "Password length is too short, it must be more than 4 characters\n";
		}
		if (user.getFirstName().length() == 0) {
			errors += "First name cannot be empty\n";
		}
		if (user.getLastName().length() == 0) {
			errors += "Last name cannot be empty\n";
		}
		if(! isValidEmail(user.getEmail())) {
			errors += "Email is not valid\n";
		}
		if(user.getPhone().length() < 11) {
			errors += "Phone number length is too short, it must be at least 11 digits\n";
		}		
		if(user.getShppingAddress().length() == 0) {
			errors += "Shipping address cannot be empty\n";
		}		
		return errors;
	}

	private boolean isValidEmail(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
}
