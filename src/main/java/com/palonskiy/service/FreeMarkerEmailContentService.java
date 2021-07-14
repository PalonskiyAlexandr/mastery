package com.palonskiy.service;

import java.util.Map;

public interface FreeMarkerEmailContentService {
    String getEmailContent(Map<String, String> model);
}
