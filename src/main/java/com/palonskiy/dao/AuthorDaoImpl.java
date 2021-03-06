package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.BookAuthor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private Long id;

    private SessionFactory sessionFactory;

    public AuthorDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Author> get() {
        return currentSession().createQuery("from Author").list();
    }

    @Override
    public List<BookAuthor> getAuthorBooks() {
        return currentSession().createQuery("from BookAuthor").list();
    }

    @Override
    public Author getById(Long id) {
        String hql = "from Author b where b.id = :id";
        return currentSession().createQuery(hql, Author.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void add(Author author) {
        currentSession().save(author);
        id = author.getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void delete(Long id) {
        currentSession().delete(getById(id));
    }

    @Override
    public Author checkIfExist(String secondName) {
        try {
            String hql = "from Author b where b.secondName = :secondName";
            return currentSession().createQuery(hql, Author.class)
                    .setParameter("secondName", secondName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(Author author) {
        currentSession().update(author);
    }

}
