package com.bridgelabz.BookStore.exception;

public class BookOrUserNotFoundException extends RuntimeException {
    public BookOrUserNotFoundException(String message) {
        super(message);
    }
}

