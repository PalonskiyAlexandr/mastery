package com.palonskiy.service;

import com.palonskiy.dao.UserDao;
import com.palonskiy.model.VerificationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private VerificationTokenService verificationTokenService;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder,
                           VerificationTokenService verificationTokenService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByName(username)
                .map(user -> new User(
                        user.getLogin(),
                        user.getPassword(),
                        user.isEnabled(),
                        true,
                        true,
                        true,
                        user.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                .collect(Collectors.toList())))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: {0} not found", username)));
    }

    public String signUpUser(com.palonskiy.model.User user){
        boolean userExist = userDao.findByName(user.getLogin()).isPresent();
        /*TODO findByEmail*/
        if(userExist){
            throw new IllegalStateException("login already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60*24),
                user
        );
        verificationTokenService.saveVerificationToken(verificationToken);
        return token;
    }

    @Override
    public void enableAppUser(String login) {
        userDao.enableUser(login);
    }
}
