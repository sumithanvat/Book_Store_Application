package com.bridgelabz.BookStore.repository;

import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.model.Cart;
import com.bridgelabz.BookStore.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookStoreCartRepository extends JpaRepository<Cart,Integer> {
    @Query(value="select * from cart where book_id =:bookId",nativeQuery =true)
    Optional<Cart> findByBookId(Integer bookId);

    @Query(value = "SELECT * FROM cart  WHERE user_id = :userId AND book_id = :bookId",nativeQuery = true)
    Cart findByUserAndBookAndQuantity(Integer userId,Integer bookId);
}

