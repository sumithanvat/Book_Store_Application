package com.bridgelabz.BookStore.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
@Data
@Entity
@Table(name = "Order_Table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int price;
    private   int Quantity;
    private String address;
    private int userId;
    private int bookId;
    boolean cancel;

    public Order(Integer price, int quantity, String address, Book book, UserRegistration userRegistration, Object cancel) {
    }
}
