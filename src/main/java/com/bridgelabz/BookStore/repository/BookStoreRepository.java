package com.bridgelabz.BookStore.repository;

import com.bridgelabz.BookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookStoreRepository extends JpaRepository<Book,Integer> {

    @Query(value = "select * from book where book_name like :bookName%", nativeQuery = true)
    List<Book> findByBookName(String bookName);
}

