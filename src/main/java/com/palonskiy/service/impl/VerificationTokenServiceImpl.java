package com.palonskiy.service.impl;

import com.palonskiy.dao.VerificationTokenDao;
import com.palonskiy.model.User;
import com.palonskiy.model.VerificationToken;
import com.palonskiy.service.VerificationTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenDao verificationTokenDao;

    public VerificationTokenServiceImpl(VerificationTokenDao verificationTokenDao) {
        this.verificationTokenDao = verificationTokenDao;
    }

    public void saveVerificationToken(VerificationToken token) {
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

    @Override
    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60 * 24),
                user
        );
        saveVerificationToken(verificationToken);
        return token;
    }
}
