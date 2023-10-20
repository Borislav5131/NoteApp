package com.example.noteapp;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validation {
    public static boolean ValidateTitle(String title) {
        final String regex = "^[a-zA-Z0-9 ]+$";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(title);

        return matcher.find();
    }

    public static boolean ValidateDescription(String description) {
        final String regex = "^[a-zA-Z0-9.,!?'\"()\\s]+$";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(description);

        return matcher.find();
    }

    public static boolean ValidateStatus(String status) {
        String[] validStatusOptions = {"ToDo", "Active", "Completed", "Pending"};

        return Arrays.asList(validStatusOptions).contains(status);
    }
}
