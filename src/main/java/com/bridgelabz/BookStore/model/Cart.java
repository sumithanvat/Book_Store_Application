package com.bridgelabz.BookStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue
    private Integer cartId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserRegistration user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private Integer quantity;
    private Double totalPrice;
//    Calculate total price based on quantity and book price
    public void calculateTotalPrice() {
        if (book != null && quantity != null) {
            totalPrice = (double) (quantity * book.getPrice());
        }
    }

    public Cart(Integer cartId, Integer quantity, Book book, UserRegistration user) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.book = book;
        this.user = user;
        calculateTotalPrice(); // Calculate total price when initializing
    }

    public Cart(Integer quantity, Book book, UserRegistration user) {
        this.quantity = quantity;
        this.book = book;
        this.user = user;
        calculateTotalPrice(); // Calculate total price when initializing
    }

    public Cart() {
        super();
    }
}
