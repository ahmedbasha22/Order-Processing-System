package application.model;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	public String newUser(User user) {
		String errors = "";
		if (user.getUsername().length() < 8) {
			errors += "Username length is too short, it must be more than 8 characters\n";
		}
		if (user.getPassword().length() == 0) {
			errors += "Please insert your password\n";
		} else if (user.getPassword().length() < 4) {
			errors += "Password length is too short, it must be more than 4 characters\n";
		}
		if (user.getFirstName().length() == 0) {
			errors += "First name cannot be empty\n";
		}
		if (user.getLastName().length() == 0) {
			errors += "Last name cannot be empty\n";
		}
		if (!isValidEmail(user.getEmail())) {
			errors += "Email is not valid\n";
		}
		if (user.getPhone().length() < 11) {
			errors += "Phone number length is too short, it must be at least 11 digits\n";
		}
		if (user.getShppingAddress().length() == 0) {
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

	
	public boolean isValidCreditCard(long creditCardNum, Date expiryDate) {
		return isValidCreditCardNum(creditCardNum) && isValidExpiryDate(expiryDate);
	}

	private boolean isValidExpiryDate(Date expiryDate) {
		return expiryDate.after(new Date(Calendar.getInstance().getTimeInMillis()));
	}

	// Return true if the card number is valid
	public boolean isValidCreditCardNum(long number) {
		return (getSize(number) >= 13 && getSize(number) <= 16)
				&& (prefixMatched(number, 4) || prefixMatched(number, 5) || prefixMatched(number, 37)
						|| prefixMatched(number, 6))
				&& ((sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0);
	}

	// Get the result from Step 2
	public int sumOfDoubleEvenPlace(long number) {
		int sum = 0;
		String num = number + "";
		for (int i = getSize(number) - 2; i >= 0; i -= 2)
			sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

		return sum;
	}

	// Return this number if it is a single digit, otherwise,
	// return the sum of the two digits
	public int getDigit(int number) {
		if (number < 9)
			return number;
		return number / 10 + number % 10;
	}

	// Return sum of odd-place digits in number
	public int sumOfOddPlace(long number) {
		int sum = 0;
		String num = number + "";
		for (int i = getSize(number) - 1; i >= 0; i -= 2)
			sum += Integer.parseInt(num.charAt(i) + "");
		return sum;
	}

	// Return true if the digit d is a prefix for number
	public boolean prefixMatched(long number, int d) {
		return getPrefix(number, getSize(d)) == d;
	}

	// Return the number of digits in d
	public static int getSize(long d) {
		String num = d + "";
		return num.length();
	}

	// Return the first k number of digits from
	// number. If the number of digits in number
	// is less than k, return number.
	public long getPrefix(long number, int k) {
		if (getSize(number) > k) {
			String num = number + "";
			return Long.parseLong(num.substring(0, k));
		}
		return number;
	}

}
