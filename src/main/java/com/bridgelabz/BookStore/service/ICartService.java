package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.CartDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.model.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICartService {
    Cart addItemToCart(CartDTO cartDTO, String token);

    ResponseDTO getCartDetails(String token);


    ResponseEntity<ResponseDTO> getCartDetailsById(Integer cartId);

    ResponseEntity<ResponseDTO> deleteCartItemById(Integer cartId);

    ResponseEntity<ResponseDTO> updateRecordById(Integer cartId, CartDTO cartDTO);

    Cart increaseQuantity(Integer id) throws BookNotFoundException;



    ResponseEntity<ResponseDTO> insertItems(CartDTO cartdto);


//    List<Cart> getUserCart(Long userId);
//
//    List<Cart> getUserCart(Integer userId);

    Cart decreaseQuantity(Integer id) throws BookNotFoundException;

    List<Cart> getUserCart(Integer userId);
}
