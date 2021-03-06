package com.palonskiy.dao;


import com.palonskiy.model.BookAuthor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookAuthorDaoImpl implements BookAuthorDao {

    private SessionFactory sessionFactory;

    public BookAuthorDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(BookAuthor bookAuthor) {
        currentSession().save(bookAuthor);
    }

    @Override
    public List<BookAuthor> get() {
        return currentSession().createQuery("from BookAuthor").list();
    }

    @Override
    public List<BookAuthor> findBooksByAuthor(Long authorId) {
        String hql = "from BookAuthor where author_id = :id";
        return currentSession().createQuery(hql, BookAuthor.class)
                .setParameter("id", authorId)
                .list();
    }

    @Override
    public List<BookAuthor> findAuthorsByBook(Long bookId) {
        String hql = "from BookAuthor where author_id = :id";
        return currentSession().createQuery(hql, BookAuthor.class)
                .setParameter("id", bookId)
                .list();
    }
}
