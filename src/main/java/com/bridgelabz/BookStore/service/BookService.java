package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.BookDTO;
import com.bridgelabz.BookStore.exception.BookStoreException;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.repository.BookStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {

    @Autowired
    BookStoreRepository bookStoreRepository;

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book(bookDTO);
        return bookStoreRepository.save(newBook);
    }

    @Override
    public Book getBookDataById(int bookId) {
        return bookStoreRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("Book Store Details for id not found"));
    }

    @Override
    public List<Book> getAllBookData() {
        List<Book> getBooks = bookStoreRepository.findAll();
        if (getBooks.isEmpty()) {
            throw new BookStoreException("No books found");
        }
        return getBooks;
    }

    @Override
    public Book updateRecordById(Integer bookId, BookDTO bookDTO) {
        Book existingBook = bookStoreRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("Book record does not found"));
        existingBook.updateFromDTO(bookDTO);
        return bookStoreRepository.save(existingBook);
    }

    @Override
    public String deleteRecordByToken(int bookId) {
        Book existingBook = bookStoreRepository.findById(bookId)
                .orElseThrow(() -> new BookStoreException("Book record does not found"));
        bookStoreRepository.delete(existingBook);
        return "Data deleted successfully";
    }

    @Override
    public List<Book> getBookByName(String bookName) {
        List<Book> findBook = bookStoreRepository.findByBookName(bookName);
        if (findBook.isEmpty()) {
            throw new BookStoreException("Details for provided Book is not found");
        }
        return findBook;
    }

    @Override
    public Book updateQuantity(Integer id, Integer quantity) {
        Book book = bookStoreRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("Book Record doesn't exist"));
        book.setQuantity(quantity);
        bookStoreRepository.save(book);
        log.info("Quantity for book record updated successfully for id " + id);
        return book;
    }
}