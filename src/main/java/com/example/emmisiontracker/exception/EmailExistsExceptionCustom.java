package com.example.emmisiontracker.exception;


public class EmailExistsExceptionCustom extends RuntimeException {

    public EmailExistsExceptionCustom(String email) {
        super("There is an account with that email address: " + email + ". Consider logging in instead.");
    }

}
