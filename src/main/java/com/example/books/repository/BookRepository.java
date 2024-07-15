package com.example.books.repository;

import com.example.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * BookRepository class.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds a book if it contains provided value in its title.
     * @param title String - provided value
     * @return List of books
     */
    List<Book> findByTitleContaining(String title);
}
