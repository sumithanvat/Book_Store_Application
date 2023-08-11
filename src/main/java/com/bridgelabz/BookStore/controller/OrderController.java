package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.OrderDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.model.Order;
import com.bridgelabz.BookStore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertOrder(@Valid @RequestBody OrderDTO orderDTO){
        Order newOrder = orderService.insertOrder(orderDTO);
        ResponseDTO dto = new ResponseDTO("User registered successfully and order record inserted successfully!",newOrder);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }
    @GetMapping("/retrieveAllOrders")
    public ResponseEntity<ResponseDTO> getAllOrderRecords(){
        List<Order> newOrder = orderService.getAllOrderRecords();
        ResponseDTO dto = new ResponseDTO("All records retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @GetMapping("/retrieveOrder/{id}")
    public ResponseEntity<ResponseDTO> getBookRecord(@PathVariable Integer id){
        Order newOrder = orderService.getOrderRecord(id);
        ResponseDTO dto = new ResponseDTO("Record retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<ResponseDTO> deleteOrderRecord(@PathVariable Integer id){
        Order newOrder = orderService.deleteOrderRecord(id);
        ResponseDTO dto = new ResponseDTO("Record deleted successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
}
