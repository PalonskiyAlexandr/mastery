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

        Object obj = new Object();

        when(sessionFactory.getCurrentSession()).thenReturn(session);


        //when
        crudDao.delete(id);

        //then
        verify(session).delete(id);
    }

    @Test
    void getById() {
        // given
        Object obj = new Object();
        long id = 13L;

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(clazz)).thenReturn(cQuery);
        when(cQuery.from(clazz)).thenReturn(tRoot);
        when(session.createQuery(cQuery)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(obj);

        //when
        crudDao.getById(id);


        //then
        verify(query).getSingleResult();
    }

    @Test
    void getByField() {
        // given
        List<Object> list = new ArrayList<>();
        Object fieldObj = mock(Object.class);
        String fieldName = "firstName";

        Session session = mock(Session.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery cQuery = mock(CriteriaQuery.class);
        Root tRoot = mock(Root.class);
        Query query = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(clazz)).thenReturn(cQuery);
        when(cQuery.from(clazz)).thenReturn(tRoot);
        when(session.createQuery(cQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(list);


        //when
        crudDao.getByField(fieldObj, fieldName);


        //then
        verify(query).getResultList();
    }
}