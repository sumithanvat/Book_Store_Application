package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.CartDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.exception.BookOrUserNotFoundException;
import com.bridgelabz.BookStore.exception.BookStoreException;
import com.bridgelabz.BookStore.exception.CartNotFoundException;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.model.Cart;
import com.bridgelabz.BookStore.model.UserRegistration;
import com.bridgelabz.BookStore.repository.BookStoreCartRepository;
import com.bridgelabz.BookStore.repository.BookStoreRepository;
import com.bridgelabz.BookStore.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService implements ICartService {

    @Autowired
    BookStoreRepository bookStoreRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    BookStoreCartRepository bookStoreCartRepository;



    @Override
    public ResponseDTO getCartDetails() {
        List<Cart> getCartDetails = bookStoreCartRepository.findAll();
        ResponseDTO dto = new ResponseDTO();
        if (getCartDetails.isEmpty()) {
            throw new CartNotFoundException("Not found any cart details");
        } else {
            dto.setMessage("The list of cart items is successfully retrieved");
            dto.setData(getCartDetails);
            return new ResponseEntity<>(dto, HttpStatus.OK).getBody();
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> getCartDetailsById(Integer cartId) {
        Cart getCartData = bookStoreCartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Didn't find any record for this particular cartId"));

        ResponseDTO dto = new ResponseDTO("Cart details retrieved successfully", getCartData);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteCartItemById(Integer cartId) {
        Cart deleteData = bookStoreCartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Didn't get any cart for the specified cart id"));

        bookStoreCartRepository.deleteById(cartId);
        ResponseDTO dto = new ResponseDTO("Cart item deleted successfully", deleteData);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ResponseDTO> updateRecordById(Integer cartId, CartDTO cartDTO) {
        Cart cart = bookStoreCartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Record doesn't exist"));

        Book book = bookStoreRepository.findById(cartDTO.getBookId())
                .orElseThrow(() -> new BookOrUserNotFoundException("Book doesn't exist"));

        UserRegistration user = userRegistrationRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new BookOrUserNotFoundException("User doesn't exist"));

        Cart newCart = new Cart(cartId, cartDTO.getQuantity(), book, user);
        bookStoreCartRepository.save(newCart);
        ResponseDTO dto = new ResponseDTO("Cart record updated successfully", newCart);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @Override
    public Cart increaseQuantity(Integer id) throws BookNotFoundException {
        Cart cart = bookStoreCartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException("Cart Record with ID " + id + " doesn't exist");
        } else {
            Book book = bookStoreRepository.findById(cart.getBook().getBookId()).orElse(null);
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + cart.getBook().getBookId() + " not found");
            }

            if (cart.getQuantity() < book.getQuantity()) {
                cart.setQuantity(cart.getQuantity() + 1);
                bookStoreCartRepository.save(cart);
                log.info("Quantity in cart record updated successfully");
                return cart;
            } else {
                throw new BookStoreException("Requested quantity is not available");
            }
        }
    }



    @Override
    public ResponseEntity<ResponseDTO> insertItems(CartDTO cartdto) {
        Book book = bookStoreRepository.findById(cartdto.getBookId())
                .orElseThrow(() -> new BookOrUserNotFoundException("Book doesn't exist"));

        UserRegistration userRegistration = userRegistrationRepository.findById(cartdto.getUserId())
                .orElseThrow(() -> new BookOrUserNotFoundException("User doesn't exist"));

        Cart newCart = new Cart(cartdto.getQuantity(), book, userRegistration);
        bookStoreCartRepository.save(newCart);
        ResponseDTO dto = new ResponseDTO("Items added to cart successfully", newCart);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    }

