package com.example.books.service;

import com.example.books.model.User;
import com.example.books.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService {

    public List<com.example.books.dto.Book> getBooksDtoList(List<Book> bookList) {
        final List<com.example.books.dto.Book> result = new ArrayList<>();
        for (Book book:bookList) {
            com.example.books.dto.Book convertedBook = getBookDto(book);
            result.add(convertedBook);
        }
        return result;
    }

    public com.example.books.dto.Book getBookDto(Book book) {
        final com.example.books.dto.Book result = new com.example.books.dto.Book();
        final com.example.books.dto.User authorDto = getUserDto(book.getAuthor());
        result.setAuthor(authorDto);
        result.setId(book.getId());
        result.setDescription(book.getDescription());
        result.setTitle(book.getTitle());
        result.setPrice(book.getPrice());
        result.setCoverImage(book.getCoverImage());
        return result;
    }

    public com.example.books.dto.User getUserDto(User user) {
        final com.example.books.dto.User result = new com.example.books.dto.User();
        result.setId(user.getId());
        result.setPseudonym(user.getPseudonym());
        result.setUsername(user.getUsername());
        return result;
    }

    public Book getBook(com.example.books.dto.Book bookDto) {
        final Book result = new Book();
        final User user = getUser(bookDto.getAuthor());
        result.setAuthor(user);
        result.setId(bookDto.getId());
        result.setDescription(bookDto.getDescription());
        result.setTitle(bookDto.getTitle());
        result.setPrice(bookDto.getPrice());
        result.setCoverImage(bookDto.getCoverImage());
        return result;
    }

    public User getUser(com.example.books.dto.User userDto) {
        final User result = new User();
        result.setId(userDto.getId());
        result.setPseudonym(userDto.getPseudonym());
        result.setUsername(userDto.getUsername());
        return result;
    }
}
