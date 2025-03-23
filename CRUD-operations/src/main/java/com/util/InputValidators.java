package com.util;

public class InputValidators {
    private static final String CONTACT_REGEX = "^[0-9]{10}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$";
    private  static final String USERNAME_REGEX = "^[a-zA-Z0-9_.-]{4,16}$";

    public static Boolean EmailValidator(String email) {
      return email != null && email.matches(EMAIL_REGEX);
    }

    public static Boolean ContactValidator(String contact) {
        return contact != null && contact.matches(CONTACT_REGEX);
    }

    public static Boolean PasswordValidator(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public static Boolean UsernameValidator(String username) {
        return username != null && username.matches(USERNAME_REGEX);
    }

}
