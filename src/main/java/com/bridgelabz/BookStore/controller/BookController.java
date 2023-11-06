package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.BookDTO;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.model.Book;
import com.bridgelabz.BookStore.service.BookService;
import com.bridgelabz.BookStore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

    @RestController
    @CrossOrigin(allowedHeaders = "*",origins = "*")

    @RequestMapping("/book")
    public class BookController {

        @Autowired
        IBookService bookService;

        // Add Data to repo
        @PostMapping("/insert")
        public ResponseEntity<String> addBookToRepository(@Valid @RequestBody BookDTO bookDTO){
            Book newBook= bookService.createBook(bookDTO);
            ResponseDTO responseDTO=new ResponseDTO("New Book Added in Book Store",newBook);
            return new ResponseEntity(responseDTO, HttpStatus.CREATED);
        }

        //Get All
        @GetMapping(value = "/getAll")
        public ResponseEntity<String> getAllBookData()
        {
            List<Book> listOfBooks = bookService.getAllBookData();
            ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:",listOfBooks);
            return new ResponseEntity(dto,HttpStatus.OK);
        }

        //Get All by BookId
        @GetMapping(value = "/getBy/{BookId}")
        public ResponseEntity<String> getBookDataById(@PathVariable int BookId)
        {
            Book Book = bookService.getBookDataById(BookId);
            ResponseDTO dto = new ResponseDTO("Data retrieved successfully by id (:",Book);
            return new ResponseEntity(dto,HttpStatus.OK);
        }

        //Delete by id
        @DeleteMapping("/delete/{BookId}")
        public ResponseEntity<String> deleteRecordById(@PathVariable int BookId){
            ResponseDTO dto = new ResponseDTO("Book Record deleted successfully", bookService.deleteRecordByToken(BookId));
            return new ResponseEntity(dto,HttpStatus.OK);
        }
        @GetMapping("searchByBookName/{name}")
        public ResponseEntity<ResponseDTO> getBookByName(@PathVariable("name") String name)
        {
            List<Book> listOfBooks = bookService.getBookByName(name);
            ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:",listOfBooks);
            return new ResponseEntity(dto,HttpStatus.OK);
        }

        //update record by id
        @PutMapping("/updateBookById/{BookId}")
        public ResponseEntity<String> updateRecordById(@PathVariable Integer BookId,@Valid @RequestBody BookDTO bookDTO){
            Book updateRecord = bookService.updateRecordById(BookId,bookDTO);
            ResponseDTO dto = new ResponseDTO(" Book Record updated successfully by Id",updateRecord);
            return new ResponseEntity(dto,HttpStatus.ACCEPTED);
        }
        @PutMapping("/updateQuantity/{id}")
        public ResponseEntity<ResponseDTO> updateQuantity(@PathVariable Integer id,@RequestBody Integer quantity){
            Book newBook = bookService.updateQuantity(id,quantity);
            ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !",newBook);
            return new ResponseEntity(dto,HttpStatus.OK);
        }
}
