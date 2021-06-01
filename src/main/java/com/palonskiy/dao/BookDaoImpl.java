package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BookDaoImpl extends CrudDaoImpl<Book> implements BookDao {

    private static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    public BookDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Book.class);
    }

    @Override
    public Author getBookAuthor(Long bookId) {
        logger.debug("getting authors of book with id {}", bookId);
        String hql = "SELECT a FROM Author a INNER JOIN a.books b WHERE b.id = :bookId";
        logger.debug("getting authors of book with id:{}", bookId);
        return currentSession().createQuery(hql, Author.class)
                .setParameter("bookId", bookId)
                .getSingleResult();
    }

    @Override
    public boolean checkIfExist(String name) {
        logger.debug("finding existing book by name {}", name);
        try {
            CriteriaBuilder cb = currentSession().getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<Book> tRoot = query.from(Book.class);
            query.where(cb.equal(tRoot.get("name"), name));
            currentSession().createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException e) {
            logger.warn("can not find the same already existing entity: {0}", e);
            return false;
        }

    }
}
