package com.palonskiy.service.impl;

import com.palonskiy.model.RegistrationRequest;
import com.palonskiy.service.EmailService;
import com.palonskiy.service.FreeMarkerEmailContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final FreeMarkerEmailContentService freeMarkerEmailContentService;
    private final ResourceBundleMessageSource messageSource;

    @Value("${message.link}")
    private String link;

    @Value("${message.from}")
    private String from;

    public EmailServiceImpl(JavaMailSender mailSender, FreeMarkerEmailContentService freeMarkerEmailContentService,
                            ResourceBundleMessageSource messageSource) {
        this.mailSender = mailSender;
        this.freeMarkerEmailContentService = freeMarkerEmailContentService;
        this.messageSource = messageSource;
    }

    private void send(String to, String email, String subject, String from) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendConfirmationEmail(String token, RegistrationRequest request, Locale locale) {
        String subject = messageSource.getMessage("message.confirmation", null, locale);
        String activation = messageSource.getMessage("message.activation", null, locale);
        String greeting = messageSource.getMessage("message.greeting", null, locale);
        Map<String, String> model = new HashMap<>();
        model.put("name", greeting + request.getName());
        model.put("link", link + token);
        model.put("activate", activation);
        send(request.getEmail(), freeMarkerEmailContentService.getEmailContent(model), subject, from);
    }


}


