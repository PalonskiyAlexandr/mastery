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

    private SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<User> findByName(String username) {
        String hql = "SELECT u FROM User u WHERE u.login = :username";
        Optional<User> optionalUser = currentSession().createQuery(hql, User.class)
                .setParameter("username", username)
                .getResultList()
                .stream().findAny();
        return optionalUser;
    }

    @Override
    public void save(User user) {
        currentSession().save(user);
    }

    @Override
    public void enableUser(String username) {
        String hql = "UPDATE User u SET u.enabled = TRUE WHERE u.login = :username";
        currentSession().createQuery(hql)
                .setParameter("username", username)
                .executeUpdate();

    }
}
