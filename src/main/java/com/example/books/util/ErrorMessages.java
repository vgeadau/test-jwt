package com.example.books.util;

/**
 * Utility class that holds all the error messages.
 */
public final class ErrorMessages {

    /**
     * {@link com.example.books.service.BookService} error messages.
     */
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String INVALID_USER = "Invalid user";
    public static final String REMOVE_NOT_ALLOWED = "You are not allowed to remove this book";
    public static final String BOOK_HAS_INVALID_USER = "Attempting to persist a book that doesn't have an existing author!";

    /**
     * {@link com.example.books.service.AuthService} error messages.
     */
    public static final String AUTHENTICATION_ERROR = "Invalid username or password";

    /**
     * {@link com.example.books.service.MyUserDetailsService} error messages.
     */
    public static final String NOT_FOUND = "Not found: ";

    /**
     * {@link com.example.books.service.ValidationService} error messages.
     */
    public static final String BANNED_USER = "That user is banned!";

    /**
     * private constructor.
     */
    private ErrorMessages() {
        throw new UnsupportedOperationException("You are not allowed to extend or instantiate this utility class");
    }
}
