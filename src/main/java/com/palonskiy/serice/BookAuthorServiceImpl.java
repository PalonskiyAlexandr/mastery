package com.palonskiy.serice;


import com.palonskiy.converters.BookAuthorConverter;
import com.palonskiy.dao.BookAuthorDao;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.model.BookAuthor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookAuthorServiceImpl implements BookAuthorService {

    BookAuthorDao bookAuthorDao;

    public BookAuthorServiceImpl(BookAuthorDao bookAuthorDao) {
        this.bookAuthorDao = bookAuthorDao;
    }

    @Override
    public void add(BookAuthorDto bookAuthorDto, Long authorId, Long bookId) {
        bookAuthorDao.add(BookAuthorConverter.toBookAuthor(bookAuthorDto, authorId, bookId));
    }

    @Override
    public List<BookAuthorDto> get() {
        return BookAuthorConverter.toBookAuthorDtoList(bookAuthorDao.get());
    }

    @Override
    public List<Long> findBooksByAuthor(Long authorId) {
        List<Long> booksId = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthorDao.findBooksByAuthor(authorId)) {
            booksId.add(bookAuthor.getBook().getId());
        }
        return booksId;
    }

    @Override
    public List<BookAuthor> findAuthorsByBook(Long bookId) {
        return bookAuthorDao.findAuthorsByBook(bookId);
    }
}
