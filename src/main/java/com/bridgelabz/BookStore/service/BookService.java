package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.BookDTO;
import com.bridgelabz.BookStore.exception.BookStoreException;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.repository.BookStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService {

    @Autowired
    BookStoreRepository bookStoreRepository;


    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book(bookDTO);
        return  bookStoreRepository.save(newBook);

    }
    public Optional<Book> getBookDataById(int BookId) {// dont use optional exception use
        Optional<Book> getBook=bookStoreRepository.findById(BookId);
        if(getBook.isEmpty()){
            throw new BookStoreException("Book Store Details for id not found");
        }
        return getBook;

    }

    public List<Book> getAllBookData() {
        List<Book> getBooks=bookStoreRepository.findAll();
        return getBooks;
    }


    public Book updateRecordById(Integer BookId, BookDTO bookDTO) {// dont use optional dry

        Optional<Book> updateBook = bookStoreRepository.findById(BookId);
        if (updateBook.isEmpty()) {
            throw new BookStoreException("Book record does not found");
        } else {
            Book updateUser = new Book(BookId, bookDTO);
            bookStoreRepository.save(updateUser);
            return updateUser;

        }
    }



    public String deleteRecordByToken(int BookId) {//dry
        Optional<Book> newBook = bookStoreRepository.findById(BookId);
        if (newBook.isEmpty()) {
            throw new BookStoreException("Book record does not found");
        } else {
            bookStoreRepository.deleteById(BookId);
        }
        return "data deleted succesfull";
    }



    public List<Book> getBookByName(String bookName) {
        List<Book> findBook= bookStoreRepository.findByBookName(bookName);
        if(findBook.isEmpty()){
            throw new BookStoreException(" Details for provided Book is not found");
        }
        return findBook;
    }

    public Book updateQuantity(Integer id, Integer quantity) {
        Optional<Book> book = bookStoreRepository.findById(id);
        if(book.isEmpty()) {
            throw new BookStoreException("Book Record doesn't exists");
        }
        else {
            book.get().setQuantity(quantity);
            bookStoreRepository.save(book.get());
            log.info("Quantity for book record updated successfully for id "+id);
            return book.get();
        }
    }


}
