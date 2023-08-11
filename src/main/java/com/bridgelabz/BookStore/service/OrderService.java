package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.OrderDTO;
import com.bridgelabz.BookStore.exception.BookStoreException;
import com.bridgelabz.BookStore.exception.ResourceNotFoundException;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.model.Order;
import com.bridgelabz.BookStore.model.UserRegistration;
import com.bridgelabz.BookStore.repository.BookStoreRepository;
import com.bridgelabz.BookStore.repository.OrderRepository;
import com.bridgelabz.BookStore.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookStoreRepository bookStoreRepository;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Override
    public Order insertOrder(OrderDTO orderDTO) {
        Book book = bookStoreRepository.findById(orderDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        UserRegistration user = userRegistrationRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (orderDTO.getQuantity() < book.getQuantity()) {
            Order newOrder = new Order(book.getPrice(), orderDTO.getQuantity(), orderDTO.getAddress(), book, user, orderDTO.isCancel());
            orderRepository.save(newOrder);
            log.info("Order record inserted successfully");
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

