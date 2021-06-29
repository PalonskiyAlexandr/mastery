package com.palonskiy.registration.service;

import com.palonskiy.registration.model.RegistrationRequest;

public interface RegistrationService {
    void register(RegistrationRequest request);

    void confirmToken(String token);
}
