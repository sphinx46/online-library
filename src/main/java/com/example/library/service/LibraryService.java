package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.util.BookNotFoundException;

import java.util.List;

public interface LibraryService {
    void addBook(Book book);
    void removeBook(String isbn) throws BookNotFoundException;
    List<Book> getAllBooks();
    Book findBookByIsbn(String isbn) throws BookNotFoundException;
    List<Book> findBooksByAuthor(String author);
    List<Book> findBooksByTitle(String title);
    void borrowBook(String isbn) throws BookNotFoundException;
    void returnBook(String isbn) throws BookNotFoundException;
}