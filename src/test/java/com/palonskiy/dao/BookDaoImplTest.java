package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookDaoImplTest {

    @Mock
    private Session session;
    @Mock
    private CriteriaBuilder cb;
    @Mock
    private Root tRoot;
    @Mock
    private Query query;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private BookDaoImpl bookDao;


    @Test
    void getBookAuthor() {
        long id = 13L;
        Author author = mock(Author.class);
        String hql = "SELECT a FROM Author a INNER JOIN a.books b WHERE b.id = :bookId";
        String parameter = "bookId";

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql, Author.class)).thenReturn(query);
        when(query.setParameter(parameter, id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(author);

        bookDao.getBookAuthors(id);

        verify(query).getSingleResult();
    }

    @Test
    void checkIfExist() {
        Object obj = new Object();
        String name = "Процесс";
        CriteriaQuery cQuery = mock(CriteriaQuery.class);


        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Book.class)).thenReturn(cQuery);
        when(cQuery.from(Book.class)).thenReturn(tRoot);
        when(session.createQuery(cQuery)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(obj);

        bookDao.checkIfExist(name);

        verify(query).getSingleResult();
    }
}