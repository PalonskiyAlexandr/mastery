package com.palonskiy.controllers;

import com.palonskiy.model.RegistrationRequest;
import com.palonskiy.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String getRegisterPage (Model model){
        model.addAttribute("request", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/registration")
    public String String (@ModelAttribute RegistrationRequest request){
        registrationService.register(request);
        return "redirect:/login";
    }

    @GetMapping("/registration/confirm")
    public String confirm (@RequestParam("token") String token){
        registrationService.confirmToken(token);
        return "redirect:/login";
    }
}
