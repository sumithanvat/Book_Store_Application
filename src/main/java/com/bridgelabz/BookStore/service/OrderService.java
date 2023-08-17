package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.OrderDTO;
import com.bridgelabz.BookStore.exception.BookStoreException;
import com.bridgelabz.BookStore.exception.ResourceNotFoundException;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.model.Cart;
import com.bridgelabz.BookStore.model.Order;
import com.bridgelabz.BookStore.model.UserRegistration;
import com.bridgelabz.BookStore.repository.BookStoreCartRepository;
import com.bridgelabz.BookStore.repository.BookStoreRepository;
import com.bridgelabz.BookStore.repository.OrderRepository;
import com.bridgelabz.BookStore.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookStoreCartRepository bookStoreCartRepository;
    @Autowired
    private BookStoreRepository bookStoreRepository;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Override
    public Order insertOrder(OrderDTO orderDTO) {
        UserRegistration user = userRegistrationRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookStoreRepository.findById(orderDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        int requestedQuantity = orderDTO.getQuantity();
        int availableQuantity = book.getQuantity();
        log.info("Order record inserted successfully");
        // Check if an existing cart entry with the same user, book, and quantity exists
        Cart existingCartEntry = bookStoreCartRepository.findByUserAndBookAndQuantity(user.getUserId(), book.getBookId());

        if (existingCartEntry != null) {
            // Remove the existing cart entry
            bookStoreCartRepository.delete(existingCartEntry);
            log.info("Existing cart entry removed due to duplicate order");
        }



        if (requestedQuantity <= availableQuantity) {
            int bookTotalPrice = requestedQuantity * book.getPrice();

            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setBook(book);
            newOrder.setPrice(bookTotalPrice); // Set the total price
            newOrder.setQuantity(requestedQuantity);
            newOrder.setAddress(orderDTO.getAddress());

            // Save the new order
            newOrder = orderRepository.save(newOrder);

            // Update book quantity
            int updatedQuantity = availableQuantity - requestedQuantity;
            book.setQuantity(updatedQuantity);
            bookStoreRepository.save(book);
            return newOrder;
    } else {
            throw new BookStoreException("Requested quantity is not available");
        }

    }


    @Override
    public List<Order> getAllOrderRecords() {
        List<Order> orderList = orderRepository.findAll();
        log.info("ALL order records retrieved successfully");
        return orderList;
    }

    @Override
    public Order getOrderRecord(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Record not found for id " + id));
        log.info("Order record retrieved successfully for id " + id);
        return order;
    }

    @Override
    public Order deleteOrderRecord(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Record not found for id " + id));
        orderRepository.deleteById(id);
        log.info("Order record deleted successfully for id " + id);
        return order;
    }
}

