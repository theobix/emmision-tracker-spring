package com.example.emmisiontracker.exception;

public class EmailFormatException extends RuntimeException {

    public EmailFormatException(String email) {
        super("The email '" + email + "'is not a valid e-mail");
    }

}
