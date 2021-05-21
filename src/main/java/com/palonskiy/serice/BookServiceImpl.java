package com.palonskiy.serice;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dao.BookDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.exceptions.ExistingEntityException;
import com.palonskiy.exceptions.NoResultException;
import com.palonskiy.exceptions.NullAuthorException;
import com.palonskiy.exceptions.NullBookException;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private AuthorDao authorDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    public List<BookDto> get() {
        return BookConverter.toDtoList(bookDao.getAll());
    }

    @Override
    public BookDto getById(int id) {
        return BookConverter.toBookDto(bookDao.getById(Long.valueOf(id)));
    }

    @Override
    public void update(BookDto bookDto) {
        bookDao.update(BookConverter.toBook(bookDto));
    }

    @Override
    public List<AuthorDto> getBookAuthors(int id) {
        return AuthorConverter.toDtoList(bookDao.getBookAuthors(Long.valueOf(id)));
    }

    @Override
    //ультра-мусор
    public void add(BookAuthorDto bookAuthorDto) {
        if (bookAuthorDto.getBook() != null) {
            if (bookAuthorDto.getAuthor() != null) {
                if (!bookDao.checkIfExist(bookAuthorDto.getBook().getName())) {
                    Long bookId = bookDao.add(BookConverter.toBook(bookAuthorDto.getBook()));
                    Long authorId = authorDao.add(AuthorConverter.toAuthor(bookAuthorDto.getAuthor()));
                    Book book = bookDao.getById(bookId);
                    Author author = authorDao.getById(authorId);
                    book.setAuthors(Arrays.asList(authorDao.getById(authorId)));
                    bookDao.update(book);
                    author.setBooks(Arrays.asList(bookDao.getById(bookId)));
                    authorDao.update(author);

                } else throw new ExistingEntityException();

            } else throw new NullBookException();
        } else
            throw new NullAuthorException();
    }

    @Override
    public Long addOnlyBook(BookDto bookDto) {
         return bookDao.add(BookConverter.toBook(bookDto));
    }


    @Override
    public void delete(int id) {
        bookDao.delete(Long.valueOf(id));
    }

    @Override
    public Boolean checkIfExist(String name) {
        return bookDao.checkIfExist(name);
    }

   /* @Override
    public List<BookAuthorDto> sortByYear(int year) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getBook().getYear().getYear() == year))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortByName(String name) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getBook().getName().equals(name)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortByPublisher(String name) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getBook().getPublisher().equals(name)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }*/
}
