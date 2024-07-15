package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.model.User;
import com.example.books.repository.BookRepository;
import com.example.books.repository.UserRepository;
import com.example.books.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BookService}.
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final static String TITLE = "some title";
    private final static Long ID = 1L;
    private final static Long INVALID_ID = 0L;
    private final static String DIFFERENT_USERNAME = "different username";

    @Mock
    private BookRepository bookRepository = Mockito.mock(BookRepository.class);

    @Mock
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Mock
    private MyUserDetailsService myUserDetailsService = Mockito.mock(MyUserDetailsService.class);

    @Mock
    private MapperService mapperService = Mockito.mock(MapperService.class);

    @InjectMocks
    private BookService target;

    @Test
    public void getBooks_withoutTitle_should_succeed() {
        // given
        List<Book> books = new ArrayList<>();
        List<com.example.books.dto.Book> bookDtos = new ArrayList<>();

        when(bookRepository.findAll()).thenReturn(books);
        when(mapperService.getBooksDtoList(books)).thenReturn(bookDtos);

        // when
        List<com.example.books.dto.Book> result = target.getBooks(null);

        // then
        verify(bookRepository).findAll();
        verify(mapperService).getBooksDtoList(books);
        verifyNoMoreInteractions(bookRepository, mapperService);
        verifyNoInteractions(userRepository, myUserDetailsService);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getBooks_withTitle_should_succeed() {
        // given
        List<Book> books = new ArrayList<>();
        List<com.example.books.dto.Book> bookDtos = new ArrayList<>();

        when(bookRepository.findByTitleContaining(TITLE)).thenReturn(books);
        when(mapperService.getBooksDtoList(books)).thenReturn(bookDtos);

        // when
        List<com.example.books.dto.Book> result = target.getBooks(TITLE);

        // then
        verify(bookRepository).findByTitleContaining(TITLE);
        verify(mapperService).getBooksDtoList(books);
        verifyNoMoreInteractions(bookRepository, mapperService);
        verifyNoInteractions(userRepository, myUserDetailsService);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getBookById_should_succeed() {
        // given
        Book book = buildBook();
        com.example.books.dto.Book bookDto = buildBookDto();

        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(mapperService.getBookDto(book)).thenReturn(bookDto);

        // when
        com.example.books.dto.Book result = target.getBookById(ID);

        // then
        verify(bookRepository).findById(ID);
        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(userRepository, myUserDetailsService);

        assertEquals(bookDto, result);
    }

    @Test
    public void getBookById_withInvalidId_should_fail() {
        // given
        when(bookRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(RuntimeException.class,
                () -> target.getBookById(INVALID_ID));

        // then
        verify(bookRepository).findById(INVALID_ID);
        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(userRepository, myUserDetailsService);

        assertEquals(ErrorMessages.BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void saveBook_withNullUser_should_fail() {
        // given
        com.example.books.dto.Book book = buildBookDto();
        book.setAuthor(null);

        // when
        Exception exception = assertThrows(RuntimeException.class,
                () -> target.saveBook(book));

        // then
        verifyNoInteractions(bookRepository, userRepository, myUserDetailsService);

        assertEquals(ErrorMessages.INVALID_USER, exception.getMessage());
    }

    @Test
    public void saveBook_withUserWithoutIdPseudonymAndUsername_should_fail() {
        // given
        com.example.books.dto.Book book = buildBookDto();
        book.setAuthor(new com.example.books.dto.User());

        // when
        Exception exception = assertThrows(RuntimeException.class,
                () -> target.saveBook(book));

        // then
        verifyNoInteractions(bookRepository, userRepository, myUserDetailsService);

        assertEquals(ErrorMessages.BOOK_HAS_INVALID_USER, exception.getMessage());
    }

    @Test
    public void saveBook_withUserId_should_succeed() {
        // given
        com.example.books.dto.Book book = buildBookDto();
        book.setAuthor(new com.example.books.dto.User());
        book.getAuthor().setId(ID);
        Book bookEntity = buildBook();

        User persistedUser = buildUser();

        when(userRepository.findById(ID)).thenReturn(Optional.of(persistedUser));
        when(mapperService.getBook(book)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(mapperService.getBookDto(bookEntity)).thenReturn(book);

        // when
        com.example.books.dto.Book result = target.saveBook(book);

        // then
        verify(userRepository).findById(ID);
        verify(bookRepository).save(bookEntity);
        verify(mapperService).getBook(book);
        verify(mapperService).getBookDto(bookEntity);
        verifyNoMoreInteractions(userRepository, bookRepository, mapperService);
        verifyNoInteractions(myUserDetailsService);

        assertEquals(book, result);
    }

    @Test
    public void saveBook_withUsername_should_succeed() {
        // given
        com.example.books.dto.Book book = buildBookDto();
        book.setAuthor(new com.example.books.dto.User());
        String username = "username";
        book.getAuthor().setUsername(username);
        Book bookEntity = buildBook();

        User persistedUser = buildUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(persistedUser));
        when(mapperService.getBook(book)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(mapperService.getBookDto(bookEntity)).thenReturn(book);

        // when
        com.example.books.dto.Book result = target.saveBook(book);

        // then
        verify(userRepository).findByUsername(username);
        verify(bookRepository).save(bookEntity);
        verify(mapperService).getBook(book);
        verify(mapperService).getBookDto(bookEntity);
        verifyNoMoreInteractions(userRepository, bookRepository, mapperService);
        verifyNoInteractions(myUserDetailsService);

        assertEquals(book, result);
    }

    @Test
    public void saveBook_withPseudonym_should_succeed() {
        // given
        com.example.books.dto.Book book = buildBookDto();
        book.setAuthor(new com.example.books.dto.User());
        String pseudonym = "pseudonym";
        book.getAuthor().setPseudonym(pseudonym);
        Book bookEntity = buildBook();

        User persistedUser = buildUser();

        when(userRepository.findByPseudonym(pseudonym)).thenReturn(Optional.of(persistedUser));
        when(mapperService.getBook(book)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(mapperService.getBookDto(bookEntity)).thenReturn(book);


        // when
        com.example.books.dto.Book result = target.saveBook(book);

        // then
        verify(userRepository).findByPseudonym(pseudonym);
        verify(bookRepository).save(bookEntity);
        verify(mapperService).getBook(book);
        verify(mapperService).getBookDto(bookEntity);
        verifyNoMoreInteractions(userRepository, bookRepository, mapperService);
        verifyNoInteractions(myUserDetailsService);

        assertEquals(book, result);
    }

    /**
     * getBookById and getPersistedAuthor are already fully covered by previous unit tests.
     * As a result, this is the only relevant test for updateBook method.
     */
    @Test
    public void updateBook_should_succeed() {
        // given
        com.example.books.dto.Book book = buildBookDto();
        Book savedBook = buildBook();

        when(bookRepository.findById(ID)).thenReturn(Optional.of(savedBook));
        when(userRepository.findByUsername(book.getAuthor().getUsername())).thenReturn(Optional.of(savedBook.getAuthor()));
        when(bookRepository.save(savedBook)).thenReturn(savedBook);
        when(mapperService.getBookDto(savedBook)).thenReturn(book);

        // when
        com.example.books.dto.Book result = target.updateBook(ID, book);

        // then
        verify(bookRepository).findById(ID);
        verify(userRepository).findByUsername(book.getAuthor().getUsername());
        verify(bookRepository).save(savedBook);
        verify(mapperService).getBookDto(savedBook);
        verifyNoMoreInteractions(bookRepository, userRepository, mapperService);
        verifyNoInteractions(myUserDetailsService);

        assertEquals(book, result);
    }

    @Test
    public void deleteBook_should_succeed() {
        // given
        Book book = buildBook();
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(myUserDetailsService.getCurrentUsername()).thenReturn(book.getAuthor().getUsername());

        // when
        boolean result = target.deleteBook(ID);

        // then
        verify(bookRepository).findById(ID);
        verify(myUserDetailsService).getCurrentUsername();
        verify(bookRepository).deleteById(ID);

        assertTrue(result);
    }

    @Test
    public void deleteBook_withForDifferentAuthor_should_fail() {
        // given
        Book book = buildBook();
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(myUserDetailsService.getCurrentUsername()).thenReturn(DIFFERENT_USERNAME);

        // when
        Exception exception = assertThrows(RuntimeException.class,
                () -> target.deleteBook(ID));

        // then
        assertEquals(ErrorMessages.REMOVE_NOT_ALLOWED, exception.getMessage());
    }


    /**
     * builds an object used for testing.
     * @return Book
     */
    private Book buildBook() {
        final Book book = new Book();
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
}
