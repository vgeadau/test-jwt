package com.example.books.controller;

import com.example.books.dto.Book;
import com.example.books.service.BookService;
import com.example.books.service.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController responsible with books operations.
 * Note1: This can be further improved by having API documentation automatically generated
 * using the OPENAPI framework.
 * Note2: Second improvement is to create separate dto(s) and to convert them into Entities
 * and back using some API like ObjectMapper.
 * But as a POC (Proof of Concept) we use entities.
 * Note3: The custom get books by any subset of fields from book can be implemented using GraphQL
 * that gives us the flexibility of writing 1 endpoint while being able to filter the books in any possible
 * combinations. see <a href="https://graphql.org">Graph QL</a>. For this POC we used filter by a subpart of a title as seen below.
 * Note4: This endpoint will return either receive and answer with JSON or XML on demand
 * based on Content-Type & Accept headers
 * application/json for JSON
 * application/xml for XML
 */
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * GET either all books, or if title is provided, we filter those books with the provided title.
     * @param title String
     * @return List of Books
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<com.example.books.dto.Book> getAllBooks(@RequestParam(required = false) String title) {
        return bookService.getBooks(title);
    }

    /**
     * We return a Book by an ID.
     * @param id Long
     * @return Book object
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public com.example.books.dto.Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    /**
     * We create a book with the provided information.
     * Example JSON:
     * {
     * 		"title" : "title",
     * 		"description" : "description" ,
     * 		"coverImage" :"coverImage" ,
     *      "price" : 10,
     * 		"author" :  {
     *             "username" : "valentin"
     *      }
     * }
     * Example XML:
     * <book>
     *     <title>title</title>
     *     <description>description</description>
     *     <coverImage>coverImage</coverImage>
     *     <price>10</price>
     *     <author>
     *         <username>valentin</username>
     *     </author>
     * </book>
     *
     * @param book Book
     * @return created Book
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public com.example.books.dto.Book createBook(@RequestBody com.example.books.dto.Book book) {
        return bookService.saveBook(book);
    }

    /**
     * We update an existent Book with id, based on provided book information
     * @param id Long
     * @param book Book
     * @return updated Book
     */
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public com.example.books.dto.Book updateBook(@PathVariable Long id, @RequestBody com.example.books.dto.Book book) {
        return bookService.updateBook(id, book);
    }

    /**
     * We un-publish a Book defined by id.
     * @param id Long
     */
    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}