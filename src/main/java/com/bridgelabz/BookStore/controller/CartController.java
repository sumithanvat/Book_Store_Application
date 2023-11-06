package com.bridgelabz.BookStore.controller;
import com.bridgelabz.BookStore.dto.CartDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.exception.BookOrUserNotFoundException;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.model.Cart;
import com.bridgelabz.BookStore.model.UserRegistration;
import com.bridgelabz.BookStore.repository.BookStoreCartRepository;
import com.bridgelabz.BookStore.repository.BookStoreRepository;
import com.bridgelabz.BookStore.repository.UserRegistrationRepository;
import com.bridgelabz.BookStore.service.BookNotFoundException;
import com.bridgelabz.BookStore.service.CartService;
import com.bridgelabz.BookStore.service.ICartService;
import com.bridgelabz.BookStore.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*",origins = "*")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;
    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private BookStoreCartRepository bookStoreCartRepository;

    @Autowired
    private TokenUtility tokenUtility;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addItemToCart(@Valid @RequestHeader(name = "Authorization") String token,
                                                     @RequestBody CartDTO cartDTO) {
       Cart cart = cartService.addItemToCart(cartDTO, token);
        ResponseDTO dto = new ResponseDTO("Item added to cart successfully", cart);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getCartDetailsList(@Valid @RequestHeader(name = "Authorization") String token) {
        ResponseDTO responseDTO = cartService.getCartDetails(token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);}

        @PutMapping("/increaseQuantity/{id}")
    public ResponseEntity<ResponseDTO> increaseQuantity(@PathVariable Integer id) throws BookNotFoundException {
        Cart newCart = cartService.increaseQuantity(id);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !",newCart);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    @PutMapping("/decreaseQuantity/{id}")
    public ResponseEntity<ResponseDTO> decreaseQuantity(@PathVariable Integer id) throws BookNotFoundException {
        Cart newCart = cartService.decreaseQuantity(id);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !",newCart);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    @GetMapping("/cartList")
    public ResponseEntity<List<Cart>> getUserCart(@RequestHeader("Authorization") String token) {
        System.out.println("In controller--------");
        Integer userId = tokenUtility.decodeToken(token);

        List<Cart> cartItems = cartService.getUserCart(userId);
        System.out.println(",,,,"+cartItems);

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
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
