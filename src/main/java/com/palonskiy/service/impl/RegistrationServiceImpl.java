package com.palonskiy.service.impl;

import com.palonskiy.model.RegistrationRequest;
import com.palonskiy.model.Role;
import com.palonskiy.model.User;
import com.palonskiy.model.VerificationToken;
import com.palonskiy.service.EmailService;
import com.palonskiy.service.RegistrationService;
import com.palonskiy.service.UserService;
import com.palonskiy.service.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    public RegistrationServiceImpl(UserService userService, VerificationTokenService verificationTokenService, EmailService emailService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public void register(RegistrationRequest request, Locale locale) {
        String token = userService.signUpUser(
                new User(
                        request.getLogin(),
                        request.getPassword(),
                        request.getName(),
                        request.getSurname(),
                        Collections.singletonList(new Role("USER")),
                        request.getEmail()
                )
        );
        emailService.sendConfirmationEmail(token, request, locale);
    }

    @Override
    public void confirmToken(String token) {
        VerificationToken verificationToken = verificationTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = verificationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        verificationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                verificationToken.getUser().getLogin());
    }


}
