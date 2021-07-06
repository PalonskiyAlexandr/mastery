package com.palonskiy.service;

import com.palonskiy.model.RegistrationRequest;

public interface RegistrationService {
    void register(RegistrationRequest request);

    void confirmToken(String token);
}
