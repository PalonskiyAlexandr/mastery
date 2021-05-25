package com.palonskiy.dao;

import com.palonskiy.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CrudDaoImplTest {


    private SessionFactory sessionFactory;
    private Class clazz;

    //@InjectMocks
    private CrudDaoImpl crudDao;

    private Session session;
    private CriteriaBuilder cb;
    private CriteriaQuery cQuery;
    private Root tRoot;
    private Query query;

    @BeforeEach
    public void init() {
        clazz = Object.class;
        sessionFactory = mock(SessionFactory.class);
        crudDao = new CrudDaoImpl(sessionFactory, Object.class) {};

        session = mock(Session.class);
        cb = mock(CriteriaBuilder.class);
        cQuery = mock(CriteriaQuery.class);
        tRoot = mock(Root.class);
        query = mock(Query.class);


    }

    private final String FIELD_NAME = "firstName";

    @Test
    void getAll() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(clazz)).thenReturn(cQuery);
        when(cQuery.from(clazz)).thenReturn(tRoot);
        when(session.createQuery(any(CriteriaQuery.class))).thenReturn(query);

        crudDao.getAll();

        verify(query).getResultList();
    }

    @Test
    void update() {
        Author authorMock = mock(Author.class);
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        crudDao.update(authorMock);

        verify(session).update(authorMock);
    }

    @Test
    void add() {
       // given
        Object savedObject = new Object();
        Object entity = new Object();

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(clazz)).thenReturn(cQuery);
        when(cQuery.from(clazz)).thenReturn(tRoot);
        when(session.createQuery(any(CriteriaQuery.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(savedObject);

        //when
        Object result = crudDao.add(entity);


        //then
        verify(session).save(entity);
        assertSame(savedObject, result);
    }

    @Test
    void delete() {
        // given
        long id = 13L;

        Object savedObject = new Object();
        Object entity = new Object();

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(query.getSingleResult()).thenReturn(savedObject);

        //when
        crudDao.delete(id);

        //then
        verify(session).delete(id);
    }

    @Test
    void getById() {
       /* assertNotNull(authorDaoMock);
        authorDaoMock.getById(1L);
        verify(authorDaoMock).getById(1L);
        //TODO check return entity*/
    }

    @Test
    void getByField() {
        /*assertNotNull(authorDaoMock);
        authorDaoMock.getByField(authorMock, FIELD_NAME);
        verify(authorDaoMock).getByField(authorMock, FIELD_NAME);
        //TODO check return entity*/
    }
}