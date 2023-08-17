package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.CartDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.model.Cart;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseDTO getCartDetails();

    ResponseEntity<ResponseDTO> getCartDetailsById(Integer cartId);

    ResponseEntity<ResponseDTO> deleteCartItemById(Integer cartId);

    ResponseEntity<ResponseDTO> updateRecordById(Integer cartId, CartDTO cartDTO);

    Cart increaseQuantity(Integer id) throws BookNotFoundException;

    ResponseEntity<ResponseDTO> insertItems(CartDTO cartdto);
}
