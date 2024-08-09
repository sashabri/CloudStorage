package com.example.cloudstorage.service;

import com.example.cloudstorage.repository.TokenRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.example.cloudstorage.controller.JwtAuthenticationFilter.BEARER_PREFIX;

@Service
public class AuthenticationService {
    private final UserInfoRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AuthenticationService(
            UserInfoRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            TokenRepository tokenRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Аутентификация пользователя
     *
     * @param userName логин пользователя
     * @param password пароль пользователя
     * @return токен
     */
    public String login(String userName, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)
        );
        var user = userRepository.getByLogin(userName);
        var token = jwtService.generateToken(user);
        tokenRepository.saveToken(token);

        log.info("successful login for user " + userName);

        return token;
    }

    public void logout(String token) {
        if (token == null || token.isEmpty()) {
            log.info("logout: token invalid");
            return;
        }
        var tokenForLogout = token;
        if (StringUtils.startsWith(token, BEARER_PREFIX)) {
            tokenForLogout = token.substring((BEARER_PREFIX.length()));
            log.info("token for logout is bearer");
        }
        tokenRepository.invalidateToken(tokenForLogout);

        log.info("successful logout for user " + jwtService.extractUserName(token));
    }
}