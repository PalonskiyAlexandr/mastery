package com.palonskiy.service;

import com.palonskiy.dao.VerificationTokenDao;
import com.palonskiy.model.VerificationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenDao verificationTokenDao;

    public VerificationTokenServiceImpl(VerificationTokenDao verificationTokenDao) {
        this.verificationTokenDao = verificationTokenDao;
    }

    public void saveVerificationToken(VerificationToken token){
        verificationTokenDao.saveVerificationToken(token);
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    @Override
    public void setConfirmedAt(String token) {
        verificationTokenDao.updateConfirmedAt(token, LocalDateTime.now());
    }
}
