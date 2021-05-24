package com.palonskiy.dao;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import com.palonskiy.serice.AuthorService;
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
public class AuthorDaoImpl extends CrudDaoImpl<Author> implements AuthorDao {

    private static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    public AuthorDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Author.class);
    }

    @Override
    public List<Book> getAuthorBooks(Long authorId) {
        String hql = "SELECT b FROM Book b INNER JOIN b.authors a WHERE a.id = :authorId";
        return currentSession().createQuery(hql, Book.class)
                .setParameter("authorId", authorId)
                .getResultList();
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
    public Boolean checkIfExist(AuthorDto authorDto) {
        try {
            CriteriaBuilder cb = currentSession().getCriteriaBuilder();
            CriteriaQuery<Author> query = cb.createQuery(Author.class);
            Root<Author> tRoot = query.from(Author.class);
            query.where(cb.equal(tRoot.get("secondName"), authorDto.getSecondName()), cb.equal(tRoot.get("firstName"), authorDto.getFirstName()));
            Author author = currentSession().createQuery(query).getSingleResult();
            logger.debug("found existing author:{}", author);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
