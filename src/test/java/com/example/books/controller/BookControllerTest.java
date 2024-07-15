package com.example.books.controller;

import com.example.books.dto.Book;
import com.example.books.dto.User;
import com.example.books.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BookController}.
 */
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private static final String TITLE = "some title";

    private static final Long ID = 1L;

    @Mock
    private BookService bookService = Mockito.mock(BookService.class);

    @InjectMocks
    private BookController target;

    @Test
    public void getAllBooks_should_succeed() {
        // given
        when(bookService.getBooks(TITLE)).thenReturn(new ArrayList<>());

        // when
        List<Book> result = target.getAllBooks(TITLE);

        // then
        verify(bookService).getBooks(TITLE);
        verifyNoMoreInteractions(bookService);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getBookById_should_succeed() {
        // given
        Book book = buildBookDto();

        when(bookService.getBookById(ID)).thenReturn(book);

        // when
        Book result = target.getBookById(ID);

        // then
        verify(bookService).getBookById(ID);
        verifyNoMoreInteractions(bookService);

        assertEquals(book, result);
    }

    @Test
    public void createBook_should_succeed() {
        // given
        Book book = buildBookDto();

        when(bookService.saveBook(book)).thenReturn(book);

        // when
        Book result = target.createBook(book);

        // then
        verify(bookService).saveBook(book);
        verifyNoMoreInteractions(bookService);

        assertEquals(book, result);
    }

    @Test
    public void updateBook_should_succeed() {
        // given
        Book book = buildBookDto();

        when(bookService.updateBook(ID, book)).thenReturn(book);

        // when
        Book result = target.updateBook(ID, book);

        // then
        verify(bookService).updateBook(ID, book);
        verifyNoMoreInteractions(bookService);

        assertEquals(book, result);
    }

    @Test
    public void deleteBook_should_succeed() {
        // given
        when(bookService.deleteBook(ID)).thenReturn(true);

        // when
        boolean result = target.deleteBook(ID);

        // then
        verify(bookService).deleteBook(ID);
        verifyNoMoreInteractions(bookService);

        assertTrue(result);
    }

    /**
     * builds an object used for testing.
     * @return Book
     */
    private Book buildBookDto() {
        final Book book = new Book();
        book.setId(2L);
        book.setAuthor(buildUserDto());
        book.setDescription("description");
        book.setPrice(10d);
        book.setCoverImage("coverImage");
        book.setTitle("title");
        return book;
    }

    /**
     * builds an object used for testing.
     * @return User
     */
    private User buildUserDto() {
        final User user = new User();
        user.setUsername("user");
        user.setPseudonym("pseudonym");
        user.setId(ID);
        return user;
    }
}
