package com.palonskiy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CrudDaoImpl<T> implements CrudDao<T> {

    protected SessionFactory sessionFactory;

    protected Class<T> clazz;

    private static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    public CrudDaoImpl(SessionFactory sessionFactory, Class<T> clazz) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<T> getAll() {
        logger.debug("getting all entities");
        CriteriaQuery<T> query = currentSession().getCriteriaBuilder().createQuery(clazz);
        Root<T> tRoot = query.from(clazz);
        query.select(tRoot);
        Query query1 = currentSession().createQuery(query);
        List<T> list= query1.getResultList();
        return currentSession().createQuery(query).getResultList();
    }

    @Override
    public void update(T obj) {
        logger.debug("getting for update {}", obj);
        currentSession().update(obj);
    }

    @Override
    public T add(T obj) {
        logger.debug("adding {}", obj);
        //TODO can't pass the test with primitive type
        return getById(((Long)currentSession().save(obj)) );
    }

    @Override
    public void delete(T obj) {
        logger.debug("deleting entity {}", obj);
        currentSession().delete(obj);
    }

    @Override
    //TODO change Long to primitive type
    public T getById(Long id) {
        logger.debug("getting by id {}", id);
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> tRoot = query.from(clazz);
        query.where(cb.equal(tRoot.get("id"), id));
        return currentSession().createQuery(query).getSingleResult();
    }

    @Override
    public  List<T>  getByField(Object obj, String fieldName) {
        logger.debug("getting by field: {} with value: {}", fieldName, obj);
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> tRoot = query.from(clazz);
        query.where(cb.equal(tRoot.get(fieldName), obj));
        return currentSession().createQuery(query).getResultList();
    }
}
