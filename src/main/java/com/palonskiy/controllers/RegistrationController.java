package com.palonskiy.controllers;

import com.palonskiy.model.RegistrationRequest;
import com.palonskiy.service.RegistrationService;
import com.palonskiy.validators.RequestValidator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RequestValidator requestValidator;

    public RegistrationController(RegistrationService registrationService, RequestValidator requestValidator) {
        this.registrationService = registrationService;
        this.requestValidator = requestValidator;
    }

    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/registration")
    public String String(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest,
                         BindingResult bindingResult, Model model) {
        requestValidator.validate(registrationRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        Locale locale = LocaleContextHolder.getLocale();
        registrationService.register(registrationRequest, locale);
        return "redirect:/login";
    }

    @GetMapping("/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return "redirect:/login";
    }
}
