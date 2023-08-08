package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.CartDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseDTO getCartDetails();

    ResponseEntity<ResponseDTO> getCartDetailsById(Integer cartId);

    ResponseEntity<ResponseDTO> deleteCartItemById(Integer cartId);

    ResponseEntity<ResponseDTO> updateRecordById(Integer cartId, CartDTO cartDTO);

    ResponseEntity<ResponseDTO> insertItems(CartDTO cartdto);
}
