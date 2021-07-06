package com.palonskiy.dao;

import com.palonskiy.model.VerificationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    private SessionFactory sessionFactory;

    public VerificationTokenDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        String hql = "SELECT t FROM VerificationToken t WHERE t.token = :token";
        Optional<VerificationToken> optionalUser = currentSession().createQuery(hql, VerificationToken.class)
                .setParameter("token", token)
                .getResultList()
                .stream().findAny();
        return optionalUser;
    }

    @Override
    public void saveVerificationToken(VerificationToken token) {
        currentSession().save(token);
    }

    @Override
    public void updateConfirmedAt(String token, LocalDateTime confirmedAt){
        String hql = "UPDATE VerificationToken v SET v.confirmedAt = :confirmedAt WHERE v.token = :token";
        currentSession().createQuery(hql)
                .setParameter("confirmedAt", confirmedAt)
                .setParameter("token", token)
                .executeUpdate();
    }
}
