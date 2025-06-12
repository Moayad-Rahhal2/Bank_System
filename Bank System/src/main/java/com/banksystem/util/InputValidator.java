package com.banksystem.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class InputValidator {

    // Jordanian phone number regex: starts with 07 followed by 7, 8, or 9, then 7 digits
    private static final Pattern PHONE_PATTERN = Pattern.compile("^07[789][0-9]{7}$");

    // Email regex pattern (basic, reliable)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Password: 8-16 chars, at least one digit, one lowercase, one uppercase
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$");

    // Validate non-empty string
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validate email format
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Validate phone format (Jordanian)
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    // Validate password strength
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    // Validate positive decimal amount
    public static boolean isPositiveAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
    // Validate account number: exactly 12 digits
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d{12}");
    }
    // Validate integer ID (positive only)
    public static boolean isValidId(String input) {
        return input != null && input.matches("^[1-9]\\d*$");
    }

}
