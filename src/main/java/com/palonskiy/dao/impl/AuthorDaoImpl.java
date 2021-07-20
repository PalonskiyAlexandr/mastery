package com.palonskiy.dao.impl;

import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AuthorDaoImpl extends CrudDaoImpl<Author> implements AuthorDao {

    private static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    public AuthorDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Author.class);
    }

    @Override
    public List<Book> getAuthorBooks(long authorId) {
        logger.debug("getting books of author with id {}", authorId);
        String hql = "SELECT b FROM Book b INNER JOIN b.authors a WHERE a.id = :authorId";
        Query query = currentSession().createQuery(hql, Book.class);
        query = query.setParameter("authorId", authorId);
        List<Book> list = query.getResultList();

        return list;

    }

    @Override
    //TODO refactor joinField method
    public List<Book> getByJoinField(Object obj, String fieldName) {
        String hql = "SELECT b FROM Book b INNER JOIN b.authors a WHERE :fieldName = :obj";
        return currentSession().createQuery(hql, Book.class)
                .setParameter("obj", obj)
                .setParameter("fieldName", fieldName)
                .getResultList();
    }

    @Override
    public boolean checkIfAuthorExist(AuthorDto authorDto) {
        logger.debug("finding existing author {}", authorDto);
        try {
            CriteriaBuilder cb = currentSession().getCriteriaBuilder();
            CriteriaQuery<Author> query = cb.createQuery(Author.class);
            Root<Author> tRoot = query.from(Author.class);
            query.where(cb.equal(tRoot.get("secondName"), authorDto.getSecondName()), cb.equal(tRoot.get("firstName"), authorDto.getFirstName()));
            currentSession().createQuery(query).getSingleResult();
            return true;
        } catch (NoResultException e) {
            logger.warn("can not find the same already existing entity: {0}", e);
            return false;
        }
    }

    @Override
    public List<Author> getAuthorExceptAuthors(BookDto bookDto) {
        String hql = "FROM Author WHERE id NOT IN (SELECT a FROM Author a JOIN a.books b where b.id = :id)";
        return currentSession().createQuery(hql, Author.class)
                .setParameter("id", bookDto.getId())
                .getResultList();
    }
}
