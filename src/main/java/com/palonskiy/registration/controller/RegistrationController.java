package com.palonskiy.registration.controller;

import com.palonskiy.registration.model.RegistrationRequest;
import com.palonskiy.registration.service.RegistrationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private RegistrationServiceImpl registrationServiceImpl;

    public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
        this.registrationServiceImpl = registrationServiceImpl;
    }

    @PostMapping("api/registration")
    public String register (@RequestBody RegistrationRequest request){
        return registrationServiceImpl.register(request);
    }

    @GetMapping("api/registration/confirm")
    public String confirm (@RequestParam("token") String token){
        return registrationServiceImpl.confirmToken(token);
    }
}
