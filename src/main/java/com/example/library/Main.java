package com.example.library;

import com.example.library.model.Book;
import com.example.library.service.LibraryService;
import com.example.library.service.LibraryServiceImpl;
import com.example.library.util.BookNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOnline Library System");
            System.out.println("1. Add a new book");
            System.out.println("2. Remove a book");
            System.out.println("3. Find a book by ISBN");
            System.out.println("4. Find books by author");
            System.out.println("5. Find books by title");
            System.out.println("6. Borrow a book");
            System.out.println("7. Return a book");
            System.out.println("8. List all books");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        addBook(libraryService, scanner);
                        break;
                    case 2:
                        removeBook(libraryService, scanner);
                        break;
                    case 3:
                        findBookByIsbn(libraryService, scanner);
                        break;
                    case 4:
                        findBooksByAuthor(libraryService, scanner);
                        break;
                    case 5:
                        findBooksByTitle(libraryService, scanner);
                        break;
                    case 6:
                        borrowBook(libraryService, scanner);
                        break;
                    case 7:
                        returnBook(libraryService, scanner);
                        break;
                    case 8:
                        listAllBooks(libraryService);
                        break;
                    case 9:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addBook(LibraryService libraryService, Scanner scanner) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        Book book = new Book(isbn, title, author);
        libraryService.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook(LibraryService libraryService, Scanner scanner) throws BookNotFoundException {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        libraryService.removeBook(isbn);
        System.out.println("Book removed successfully.");
    }

    private static void findBookByIsbn(LibraryService libraryService, Scanner scanner) throws BookNotFoundException {
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine();
        Book book = libraryService.findBookByIsbn(isbn);
        System.out.println("Found book: " + book);
    }

    private static void findBooksByAuthor(LibraryService libraryService, Scanner scanner) {
        System.out.print("Enter author name to search: ");
        String author = scanner.nextLine();
        libraryService.findBooksByAuthor(author).forEach(System.out::println);
    }

    private static void findBooksByTitle(LibraryService libraryService, Scanner scanner) {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        libraryService.findBooksByTitle(title).forEach(System.out::println);
    }

    private static void borrowBook(LibraryService libraryService, Scanner scanner) throws BookNotFoundException {
        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();
        libraryService.borrowBook(isbn);
        System.out.println("Book borrowed successfully.");
    }

    private static void returnBook(LibraryService libraryService, Scanner scanner) throws BookNotFoundException {
        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();
        libraryService.returnBook(isbn);
        System.out.println("Book returned successfully.");
    }

    private static void listAllBooks(LibraryService libraryService) {
        System.out.println("\nAll books in library:");
        if (libraryService.getAllBooks().isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.printf("%-15s %-30s %-20s %s%n", "ISBN", "Title", "Author", "Status");
            System.out.println("----------------------------------------------------------------");
            libraryService.getAllBooks().forEach(book -> {
                String status = book.isBorrowed() ? "Borrowed" : "Available";
                System.out.printf("%-15s %-30s %-20s %s%n",
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        status);
            });
        }
    }
}