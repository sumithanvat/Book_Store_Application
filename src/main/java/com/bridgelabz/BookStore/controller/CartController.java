package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.CartDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.model.Cart;
import com.bridgelabz.BookStore.service.BookNotFoundException;
import com.bridgelabz.BookStore.service.CartService;
import com.bridgelabz.BookStore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> insertItem(@Valid @RequestBody CartDTO cartdto) {
        ResponseEntity<ResponseDTO> newCart = cartService.insertItems(cartdto);
        ResponseDTO responseDTO = new ResponseDTO("User registered successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllCartDetails() {
        ResponseDTO responseDTO = cartService.getCartDetails();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/increaseQuantity/{id}")
    public ResponseEntity<ResponseDTO> increaseQuantity(@PathVariable Integer id) throws BookNotFoundException {
        Cart newCart = cartService.increaseQuantity(id);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !",newCart);
        return new ResponseEntity(dto,HttpStatus.OK);
    }


    @GetMapping("/getById/{cartId}")
    public ResponseEntity<ResponseDTO> getCartDetailsById(@PathVariable Integer cartId){
        ResponseEntity<ResponseDTO> specificCartDetail=cartService.getCartDetailsById(cartId);
        ResponseDTO responseDTO=new ResponseDTO("Cart details retrieved successfully",specificCartDetail);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable Integer cartId) {
        ResponseEntity<ResponseDTO> delete = cartService.deleteCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart delete successfully", delete);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/updateById/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Integer cartId,@Valid @RequestBody CartDTO cartDTO){
        ResponseEntity<ResponseDTO> updateRecord = cartService.updateRecordById(cartId,cartDTO);
        ResponseDTO dto = new ResponseDTO(" Cart Record updated successfully by Id",updateRecord);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
}
