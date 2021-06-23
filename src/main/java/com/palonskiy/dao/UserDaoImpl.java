package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    protected SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<User> findByName(String username) {
/*        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> tRoot = query.from(User.class);
        query.where(cb.equal(tRoot.get("login"), username));
        Query q = currentSession().createQuery(query);
        List<User> l = q.getResultList();
        Optional<User> optionalUser = currentSession().createQuery(query).getResultList().stream().findFirst();*/
        String hql = "SELECT u FROM User u WHERE u.login = :username";
        Optional<User> optionalUser = currentSession().createQuery(hql, User.class)
                .setParameter("username", username)
                .getResultList()
                .stream().findFirst();
        return optionalUser;
    }
}
