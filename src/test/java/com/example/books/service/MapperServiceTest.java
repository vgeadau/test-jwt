package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MapperServiceTest {

    private final static Long ID = 1L;

    private final MapperService target = new MapperService();

    @Test
    public void getBooksDtoList_should_succeed() {
        // given
        Book book = buildBook();
        List<Book> books = List.of(book);

        // when
        List<com.example.books.dto.Book> result = target.getBooksDtoList(books);

        // then
        assertEquals(books.size(), result.size());
        assertEquals(books.get(0).getAuthor().getUsername(), result.get(0).getAuthor().getUsername());
        assertEquals(books.get(0).getAuthor().getId(), result.get(0).getAuthor().getId());
        assertEquals(books.get(0).getAuthor().getPseudonym(), result.get(0).getAuthor().getPseudonym());
        assertEquals(books.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(books.get(0).getId(), result.get(0).getId());
        assertEquals(books.get(0).getPrice(), result.get(0).getPrice());
        assertEquals(books.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(books.get(0).getCoverImage(), result.get(0).getCoverImage());
    }

    @Test
    public void getBookDto_should_succeed() {
        // given
        Book book = buildBook();

        // when
        com.example.books.dto.Book result = target.getBookDto(book);

        // then
        assertEquals(book.getAuthor().getUsername(), result.getAuthor().getUsername());
        assertEquals(book.getAuthor().getId(), result.getAuthor().getId());
        assertEquals(book.getAuthor().getPseudonym(), result.getAuthor().getPseudonym());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getPrice(), result.getPrice());
        assertEquals(book.getDescription(), result.getDescription());
        assertEquals(book.getCoverImage(), result.getCoverImage());
    }

    @Test
    public void getUserDto_should_succeed() {
        // given
        User user = buildUser();

        // when
        com.example.books.dto.User result = target.getUserDto(user);

        // then
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getPseudonym(), result.getPseudonym());
    }

    @Test
    public void getBook_should_succeed() {
        // given
        com.example.books.dto.Book bookDto = buildBookDto();

        // when
        Book result = target.getBook(bookDto);

        // then
        assertEquals(bookDto.getAuthor().getUsername(), result.getAuthor().getUsername());
        assertEquals(bookDto.getAuthor().getId(), result.getAuthor().getId());
        assertEquals(bookDto.getAuthor().getPseudonym(), result.getAuthor().getPseudonym());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getPrice(), result.getPrice());
        assertEquals(bookDto.getDescription(), result.getDescription());
        assertEquals(bookDto.getCoverImage(), result.getCoverImage());
    }

    @Test
    public void getUser_should_succeed() {
        // given
        com.example.books.dto.User userDto = buildUserDto();

        // when
        User result = target.getUser(userDto);

        // then
        assertEquals(userDto.getUsername(), result.getUsername());
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getPseudonym(), result.getPseudonym());
    }


    /**
     * builds an object used for testing.
     * @return Book
     */
    private com.example.books.dto.Book buildBookDto() {
        final com.example.books.dto.Book book = new com.example.books.dto.Book();
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
    private com.example.books.dto.User buildUserDto() {
        final com.example.books.dto.User user = new com.example.books.dto.User();
        user.setUsername("user");
        user.setPseudonym("pseudonym");
        user.setId(ID);
        return user;
    }

    /**
     * builds an object used for testing.
     * @return Book
     */
    private com.example.books.model.Book buildBook() {
        final com.example.books.model.Book book = new com.example.books.model.Book();
        book.setAuthor(buildUser());
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
    private User buildUser() {
        final User user = new User();
        user.setUsername("user");
        user.setPseudonym("pseudonym");
        user.setId(ID);
        return user;
    }
}
