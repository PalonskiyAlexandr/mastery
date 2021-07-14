package com.palonskiy.service;

import com.palonskiy.model.RegistrationRequest;

import java.util.Locale;

public interface EmailService {
    void sendConfirmationEmail(String token, RegistrationRequest request, Locale locale);
}
