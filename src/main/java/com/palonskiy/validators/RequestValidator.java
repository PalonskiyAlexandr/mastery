package com.palonskiy.validators;

import com.palonskiy.model.RegistrationRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class RequestValidator implements Validator {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[\\w\\d._-]+@[\\w\\d.-]+\\.[\\w\\d]{2,6}$");

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == RegistrationRequest.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "login", "registrationRequest.login.empty");
        ValidationUtils.rejectIfEmpty(errors, "name", "registrationRequest.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "surname", "registrationRequest.surname.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "registrationRequest.password.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "registrationRequest.email.empty");


        RegistrationRequest registrationRequest = (RegistrationRequest) target;
        if (registrationRequest.getLogin() != null && registrationRequest.getLogin().length() < 2 ||
                registrationRequest.getLogin().length() > 20) {
            errors.rejectValue("login", "registrationRequest.login.size");
        }
        if (registrationRequest.getName() != null && registrationRequest.getName().length() < 2 ||
                registrationRequest.getName().length() > 20) {
            errors.rejectValue("name", "registrationRequest.name.size");
        }
        if (registrationRequest.getSurname() != null && registrationRequest.getSurname().length() < 2 ||
                registrationRequest.getSurname().length() > 20) {
            errors.rejectValue("surname", "registrationRequest.surname.size");
        }

        if (registrationRequest.getPassword() != null && registrationRequest.getPassword().contains(" ")) {
            errors.rejectValue("password", "registrationRequest.password.space");
        }

        if (registrationRequest.getPassword() != null && registrationRequest.getPassword().length() < 5 ||
                registrationRequest.getPassword().length() > 15) {
            errors.rejectValue("password", "registrationRequest.password.size");
        }

        if (registrationRequest.getEmail() != null && !EMAIL_REGEX.matcher(registrationRequest.getEmail()).matches()) {
            errors.rejectValue("email", "registrationRequest.email.invalid");
        }

        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatedPassword())) {
            errors.rejectValue("repeatedPassword", "registrationRequest.repeatedPassword.compare");
        }
    }
}
