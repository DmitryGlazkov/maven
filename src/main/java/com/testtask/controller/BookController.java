package com.testtask.controller;


import com.testtask.model.Book;
import com.testtask.repository.BookRepository;
import com.testtask.util.Selection;
import com.testtask.util.impl.BookSelection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/books")
    public Book postBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/select")
    public List<Book> getSelectBooks() {
        Selection<Book> selection = new BookSelection();

        return selection.someSelection(bookRepository.findAll());
    }

    @GetMapping("/books/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable(value = "title") String title) {
        Book titleBook = bookRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList())
                .get(0);

        return ResponseEntity.ok().body(titleBook);
    }

    @PutMapping("/books/{title}")
    public ResponseEntity<Book> putBook(@PathVariable(value = "title") String title,
                                        @Valid @RequestBody Book bookDetails) {

        Book book = bookRepository.findAll()
                .stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList())
                .get(0);

        book.setAuthor(bookDetails.getAuthor());
        book.setDateOfPublication((bookDetails.getDateOfPublication()));
        book.setTitle(bookDetails.getTitle());

        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findOne(id);
        if(book == null) {
            return ResponseEntity.notFound().build();
        }

        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }
}
