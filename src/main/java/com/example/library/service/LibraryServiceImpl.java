package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Library;
import com.example.library.util.BookNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryServiceImpl implements LibraryService {
    private final Library library;

    public LibraryServiceImpl() {
        this.library = new Library();
    }

    @Override
    public void addBook(Book book) {
        library.addBook(book);
    }

    @Override
    public void removeBook(String isbn) throws BookNotFoundException {
        Book bookToRemove = findBookByIsbn(isbn);
        library.removeBook(bookToRemove);
    }

    @Override
    public List<Book> getAllBooks() {
        return library.getBooks();
    }

    @Override
    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        return library.getBooks().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found"));
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return library.getBooks().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return library.getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    @Override
    public void borrowBook(String isbn) throws BookNotFoundException {
        Book book = findBookByIsbn(isbn);
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed");
        }
        book.setBorrowed(true);
    }

    @Override
    public void returnBook(String isbn) throws BookNotFoundException {
        Book book = findBookByIsbn(isbn);
        if (!book.isBorrowed()) {
            throw new IllegalStateException("Book was not borrowed");
        }
        book.setBorrowed(false);
    }
}