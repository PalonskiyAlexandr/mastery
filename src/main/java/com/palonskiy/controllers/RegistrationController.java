package com.palonskiy.controllers;

import com.palonskiy.model.RegistrationRequest;
import com.palonskiy.service.RegistrationService;
import com.palonskiy.validators.RequestValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("request", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/registration")
    public String String(@ModelAttribute RegistrationRequest request, BindingResult bindingResult, Model model) {
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("request", new RegistrationRequest());
            return "register";
        }
        registrationService.register(request);
        return "redirect:/login";
    }

    @GetMapping("/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return "redirect:/login";
    }
}
