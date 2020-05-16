package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	public List<String> newUser(User user) {
		List<String> errors = new ArrayList<String>();
		if (user.getUsername().length() < 8) {
			errors.add("Username length is too short, it must be more than 8 characters");
		}
		if (user.getPassword().length() < 8) {
			errors.add("Password length is too short, it must be more than 8 characters");
		}
		if (user.getFirstName().length() == 0) {
			errors.add("First name cannot be empty");
		}
		if (user.getLastName().length() == 0) {
			errors.add("Last name cannot be empty");
		}
		if(! isValidEmail(user.getEmail())) {
			errors.add("Email is not valid");
		}
		if(user.getPhone().length() < 11) {
			errors.add("Password length is too short, it must be at least 11 digits");
		}		
		if(user.getShppingAddress().length() == 0) {
			errors.add("Shipping address cannot be empty");
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
