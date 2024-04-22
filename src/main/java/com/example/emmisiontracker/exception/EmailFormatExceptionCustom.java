package com.example.emmisiontracker.exception;

public class EmailFormatExceptionCustom extends RuntimeException {

    public EmailFormatExceptionCustom(String email) {
        super("The email '" + email + "'is not a valid e-mail");
    }

}
