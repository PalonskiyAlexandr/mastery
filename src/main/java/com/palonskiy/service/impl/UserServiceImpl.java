package com.palonskiy.service.impl;

import com.palonskiy.converters.SecurityUserConverter;
import com.palonskiy.dao.UserDao;
import com.palonskiy.service.UserService;
import com.palonskiy.service.VerificationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final SecurityUserConverter securityUserConverter;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, VerificationTokenService verificationTokenService, SecurityUserConverter securityUserConverter) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.securityUserConverter = securityUserConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return securityUserConverter.toSecurityUser(userDao.findByLogin(username))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s not found", username)));
    }

    public String signUpUser(com.palonskiy.model.User user) {
        boolean userExist = userDao.findByLogin(user.getLogin()).isPresent();
        /*TODO findByEmail*/
        if (userExist) {
            throw new IllegalStateException("login already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);
        return verificationTokenService.createToken(user);
    }

    @Override
    public void enableAppUser(String login) {
        userDao.enableUser(login);
    }
}
