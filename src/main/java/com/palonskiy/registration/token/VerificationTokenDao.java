package com.palonskiy.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {
    Optional<VerificationToken> findByToken(String token);

    void saveVerificationToken(VerificationToken token);

    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
