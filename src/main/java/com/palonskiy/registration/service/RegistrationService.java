package com.palonskiy.registration.service;

import com.palonskiy.registration.model.RegistrationRequest;

public interface RegistrationService {
    public String register(RegistrationRequest request);

    String confirmToken(String token);
}
