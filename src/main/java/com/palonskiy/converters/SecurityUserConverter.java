package com.palonskiy.converters;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SecurityUserConverter {

    public Optional<UserDetails> toSecurityUser(Optional<com.palonskiy.model.User> user) {
        return user.map(u -> new User(
                u.getLogin(),
                u.getPassword(),
                u.isEnabled(),
                true,
                true,
                true,
                u.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .collect(Collectors.toList())));
    }
}
