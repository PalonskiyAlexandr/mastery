package com.palonskiy.dao;

import com.palonskiy.model.Book;
import com.palonskiy.model.BookAuthor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    private Long id;

    private SessionFactory sessionFactory;

    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public List<Book> get() {
        return currentSession().createQuery("from Book").list();
    }

    @Override
    public List<BookAuthor> getBookAuthors() {
        return currentSession().createQuery("from BookAuthor").list();
    }

    @Override
    public Book getById(Long id) {
        String hql = "from Book b where b.id = :id";
        return currentSession().createQuery(hql, Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void add(Book book) {
        currentSession().save(book);
        id = book.getId();
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
    public Book checkIfExist(String name) {
        try {
            String hql = "from Book b where b.name = :name";
            return currentSession().createQuery(hql, Book.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public void update(Book book) {
        currentSession().update(book);
    }

}
