package com.karan.prometheus_grafana.service;

import com.karan.prometheus_grafana.dto.BookResponseDTO;
import com.karan.prometheus_grafana.dto.CreateBookRequestDTO;
import com.karan.prometheus_grafana.model.Book;
import com.karan.prometheus_grafana.repository.BookRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public BookResponseDTO createBook(CreateBookRequestDTO createBookRequestDTO) {
        Book book = Book.builder()
                .name(createBookRequestDTO.getName())
                .author(createBookRequestDTO.getAuthor())
                .publisher(createBookRequestDTO.getPublisher())
                .description(createBookRequestDTO.getDescription())
                .build();

        Book savedBook = bookRepository.save(book);
        return BookResponseDTO.builder()
                .id(savedBook.getId())
                .name(savedBook.getName())
                .author(savedBook.getAuthor())
                .publisher(savedBook.getPublisher())
                .description(savedBook.getDescription())
                .build();

    }
    @Cacheable(value = "books",key = "#id")
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return BookResponseDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .description(book.getDescription())
                .build();
    }
    @Cacheable(value = "books")
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookResponseDTO.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .description(book.getDescription())
                        .build())
                .toList();
    }
    @CachePut(value = "books", key = "#id")
    public BookResponseDTO updateBook(Long id, CreateBookRequestDTO createBookRequestDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setName(createBookRequestDTO.getName());
        book.setAuthor(createBookRequestDTO.getAuthor());
        book.setPublisher(createBookRequestDTO.getPublisher());
        book.setDescription(createBookRequestDTO.getDescription());

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTO.builder()
                .id(updatedBook.getId())
                .name(updatedBook.getName())
                .author(updatedBook.getAuthor())
                .publisher(updatedBook.getPublisher())
                .description(updatedBook.getDescription())
                .build();
    }
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id){
        if(!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
    }

