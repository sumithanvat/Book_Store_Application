package com.bridgelabz.BookStore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Order_Table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private LocalDate date=LocalDate.now();
    private int price;
    private   int quantity;
    private String address;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserRegistration user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private boolean cancel;

    public Order(Integer orderId, Integer price, Integer quantity, String address, Book book, UserRegistration user, boolean cancel) {
        this.orderId = orderId;
        this.price=price;
        this.quantity =quantity;
        this.address=address;
        this.book = book;
        this.user = user;
        this.cancel = cancel;
    }

    public Order() {
        super();
    }

    public Order(Integer price, Integer quantity, String address, Book book, UserRegistration user, boolean cancel) {
        this.price=price;
        this.quantity =quantity;
        this.address=address;
        this.book = book;
        this.user = user;
        this.cancel = cancel;
    }

    public Order(Integer price, int quantity, String address, Book book, UserRegistration user, Object cancel) {
    }

    public Order(UserRegistration user, Book book, int requestedQuantity, double bookTotalPrice, String address) {
    }

    public double calculateTotalPrice(Book book,int quantity){
        return book.getPrice()*quantity;
    }
    public void setTotalPrice(double totalPrice){

    }
}
