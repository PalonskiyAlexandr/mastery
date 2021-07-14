package com.palonskiy.service.impl;

import com.palonskiy.service.FreeMarkerEmailContentService;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreeMarkerEmailContentServiceImpl implements FreeMarkerEmailContentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private Configuration fmConfiguration;

    @Override
    public String getEmailContent(Map<String, String> model) {
        StringWriter stringWriter = new StringWriter();
        try {
            fmConfiguration.getTemplate("registrationMessage.flth").process(model, stringWriter);
        } catch (Exception e) {
            LOGGER.error("failed to form email", e);
        }
        return stringWriter.getBuffer().toString();
    }
}
