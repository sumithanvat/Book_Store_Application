package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.OrderDTO;
import com.bridgelabz.BookStore.model.Order;

import java.util.List;

public interface IOrderService {
    Order insertOrder(OrderDTO orderdto);

    List<Order> getAllOrderRecords();

    Order getOrderRecord(Integer id);

    Order deleteOrderRecord(Integer id);
}
