package com.palonskiy.dao;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookDaoImplTest {

    private SessionFactory sessionFactory;
    private BookDaoImpl bookDao;

    private Session session;
    private CriteriaBuilder cb;
    private CriteriaQuery cQuery;
    private Root tRoot;
    private Query query;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        bookDao = new BookDaoImpl(sessionFactory) {};

        session = mock(Session.class);
        cb = mock(CriteriaBuilder.class);
        cQuery = mock(CriteriaQuery.class);
        tRoot = mock(Root.class);
        query = mock(Query.class);
    }
    @Test
    void getBookAuthors() {
        Long id = 13L;
        List<Object> list = new ArrayList<>();
        String hql = "SELECT a FROM Author a INNER JOIN a.books b WHERE b.id = :bookId";
        String parameter = "bookId";

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql, Author.class)).thenReturn(query);
        when(query.setParameter(parameter, id)).thenReturn(query);
        when(query.getResultList()).thenReturn(list);

        bookDao.getBookAuthors(id);

        verify(query).getResultList();
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