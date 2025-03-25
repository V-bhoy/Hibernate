package com.util;

public class Validators {
    private static final String CONTACT_REGEX = "^[0-9]{10}$";
    public static boolean ContactValidator(String contact) {
        return contact != null && contact.matches(CONTACT_REGEX);
    }
}
