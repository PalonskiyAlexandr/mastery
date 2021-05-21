package com.palonskiy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public abstract class CrudDaoImpl<T> implements CrudDao<T> {
    protected SessionFactory sessionFactory;
    protected Class<T> clazz;

    public CrudDaoImpl(SessionFactory sessionFactory, Class<T> clazz) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<T> getAll() {
        CriteriaQuery<T> query = currentSession().getCriteriaBuilder().createQuery(clazz);
        Root<T> tRoot = query.from(clazz);
        query.select(tRoot);
        return currentSession().createQuery(query).getResultList();
    }

    @Override
    public void update(T obj) {
        currentSession().update(obj);
    }

    @Override
    public Long add(T obj) {
        return (Long) currentSession().save(obj);
    }

    public Long addField(T obj, Long id) {
        return (Long) currentSession().save(obj);
    }

    @Override
    public void delete(Long id) {
        currentSession().delete(getById(id));
    }

    @Override
    public T getById(Long id) {
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> tRoot = query.from(clazz);
        query.where(cb.equal(tRoot.get("id"), id));
        return currentSession().createQuery(query).getSingleResult();
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
