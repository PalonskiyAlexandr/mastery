package com.palonskiy.service;

import com.palonskiy.converters.SecurityUserConverter;
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
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: {0} not found", username)));
    }

    public String signUpUser(com.palonskiy.model.User user){
        boolean userExist = userDao.findByLogin(user.getLogin()).isPresent();
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
