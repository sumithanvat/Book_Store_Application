package com.bridgelabz.BookStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookStoreApplication {

	public static void main(String[] args) {

		System.out.println("Welcome to Book Store Application");
		SpringApplication.run(BookStoreApplication.class, args);
	}

}
