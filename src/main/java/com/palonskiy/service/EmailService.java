package com.palonskiy.service;

import com.palonskiy.model.RegistrationRequest;

public interface EmailService {
    void sendConfirmationEmail(String token, RegistrationRequest request);
}
