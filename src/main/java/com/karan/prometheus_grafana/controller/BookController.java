package com.karan.prometheus_grafana.controller;

import com.karan.prometheus_grafana.dto.BookResponseDTO;
import com.karan.prometheus_grafana.dto.CreateBookRequestDTO;
import com.karan.prometheus_grafana.service.BookService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/create")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid CreateBookRequestDTO createBookRequestDTO) {
        BookResponseDTO bookResponseDTO = bookService.createBook(createBookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO bookResponseDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> bookResponseDTOS = bookService.getAllBooks();
        return ResponseEntity.ok(bookResponseDTOS);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
