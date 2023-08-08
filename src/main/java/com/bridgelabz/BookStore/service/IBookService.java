package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.BookDTO;
import com.bridgelabz.BookStore.model.Book;

import java.util.List;

public interface IBookService {
    Book createBook(BookDTO bookDTO);

    Book getBookDataById(int BookId);

    List<Book> getAllBookData();

    Book updateRecordById(Integer BookId, BookDTO bookDTO);

    String deleteRecordByToken(int BookId);

    List<Book> getBookByName(String bookName);

    Book updateQuantity(Integer id, Integer quantity);
}
