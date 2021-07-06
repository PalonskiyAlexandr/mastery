package com.palonskiy.registration.token;

import java.util.Optional;

public interface VerificationTokenService {
    void saveVerificationToken(VerificationToken token);

    void setConfirmedAt(String token);

    Optional<VerificationToken> getToken(String token);
}
