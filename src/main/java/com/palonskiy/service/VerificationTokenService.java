package com.palonskiy.service;

import com.palonskiy.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {
    void saveVerificationToken(VerificationToken token);

    void setConfirmedAt(String token);

    Optional<VerificationToken> getToken(String token);
}
