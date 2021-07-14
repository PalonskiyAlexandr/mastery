package com.palonskiy.service;

import com.palonskiy.model.RegistrationRequest;

import java.util.Locale;

public interface RegistrationService {
    void register(RegistrationRequest request, Locale locale);

    void confirmToken(String token);
}
