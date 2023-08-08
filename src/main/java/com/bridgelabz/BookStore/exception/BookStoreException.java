package com.bridgelabz.BookStore.exception;

import lombok.Data;

@Data
public class BookStoreException extends RuntimeException{
    public BookStoreException(String message){
        super(message);
    }

}

