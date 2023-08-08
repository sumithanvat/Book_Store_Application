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

    public Cart(Integer cartId, Integer quantity, Book book, UserRegistration user) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.book = book;
        this.user = user;
    }

    public Cart(Integer quantity, Book book, UserRegistration user) {
        this.quantity = quantity;
        this.book = book;
        this.user = user;
    }

    public Cart() {
        super();
    }
}
