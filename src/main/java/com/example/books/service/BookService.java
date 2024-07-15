package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.repository.BookRepository;
import com.example.books.repository.UserRepository;
import com.example.books.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Books Service class.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final MyUserDetailsService myUserDetailsService;

    private final MapperService mapperService;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository,
                       MyUserDetailsService myUserDetailsService, MapperService mapperService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.mapperService = mapperService;
    }

    /**
     * Gets books.
     * @param title String
     * @return List
     */
    public List<com.example.books.dto.Book> getBooks(String title) {
        List<Book> books;
        if (Objects.isNull(title)) {
            books = getAllBooks();
        } else {
            books = searchBooksByTitle(title);
        }
        return mapperService.getBooksDtoList(books);
    }

    /**
     * Gets a book by provided id.
     * @param id Long
     * @return Book
     */
    public com.example.books.dto.Book getBookById(Long id) {
        Book book = getPersistedBookById(id);
        return mapperService.getBookDto(book);
    }

    private Book getPersistedBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException(ErrorMessages.BOOK_NOT_FOUND));
    }

    /**
     * Creates a Book record.
     * @param bookDto Book
     * @return the persisted Book
     */
    public com.example.books.dto.Book saveBook(com.example.books.dto.Book bookDto) {
        final Book book = mapperService.getBook(bookDto);
        book.setAuthor(getPersistedAuthor(bookDto.getAuthor()));
        final Book persistedBook = bookRepository.save(book);
        return mapperService.getBookDto(persistedBook);
    }

    /**
     * Updates a Book record.
     * @param id Long
     * @param book Book
     * @return Book
     */
    public com.example.books.dto.Book updateBook(Long id, com.example.books.dto.Book book) {
        Book existingBook = getPersistedBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setDescription(book.getDescription());
        existingBook.setAuthor(getPersistedAuthor(book.getAuthor()));
        existingBook.setCoverImage(book.getCoverImage());
        existingBook.setPrice(book.getPrice());
        Book updatedBook = bookRepository.save(existingBook);
        return mapperService.getBookDto(updatedBook);
    }

    /**
     * Deletes a book record.
     * @param id Long
     */
    public boolean deleteBook(Long id) {
        Book existingBook = getPersistedBookById(id);
        String username = myUserDetailsService.getCurrentUsername();
        if (username.equals(existingBook.getAuthor().getUsername())) {
            bookRepository.deleteById(id);
            // as we reached this point we can consider delete was successfully done.
            return true;
        } else {
            throw new RuntimeException(ErrorMessages.REMOVE_NOT_ALLOWED);
        }
    }

    /**
     * Method that return the persisted author using its username.
     * @param user String
     * @return User object
     */
    private User getPersistedAuthor(com.example.books.dto.User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException(ErrorMessages.INVALID_USER);
        }

        Optional<User> author = Optional.empty();
        if (!Objects.isNull(user.getUsername())) {
            author = userRepository.findByUsername(user.getUsername());
        } else if (!Objects.isNull(user.getPseudonym())) {
            author = userRepository.findByPseudonym(user.getPseudonym());
        } else if (!Objects.isNull(user.getId())) {
            author = userRepository.findById(user.getId());
        }

        if (author.isPresent()) {
            return author.get();

        } else {
            throw new RuntimeException(ErrorMessages.BOOK_HAS_INVALID_USER);
        }
    }

    /**
     * Lists all books.
     * @return List
     */
    private List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Filter a list of books by a part of the title.
     * @param title String - the part of the title
     * @return List of books matching the search criteria
     */
    private List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }
}