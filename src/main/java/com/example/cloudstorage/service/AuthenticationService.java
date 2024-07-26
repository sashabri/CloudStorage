package com.example.cloudstorage.service;

import com.example.cloudstorage.repository.TokenRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.cloudstorage.controller.JwtAuthenticationFilter.BEARER_PREFIX;

@Service
public class AuthenticationService {
    private final UserInfoRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(
            UserInfoRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            TokenRepository tokenRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Аутентификация пользователя
     *
     * @param login логин пользователя
     * @param password пароль пользователя
     * @return токен
     */
    public String login(String login, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );
        var user = userRepository.getByLogin(login);
        var token = jwtService.generateToken(user);
        tokenRepository.saveToken(token);
        return token;
    }

    public void logout(String token) {
        if (token == null || token.isEmpty()) return;
        var tokenForLogout = token;
        if (StringUtils.startsWith(token, BEARER_PREFIX)) {
            tokenForLogout = token.substring((BEARER_PREFIX.length()));
        }
        tokenRepository.invalidateToken(tokenForLogout);
    }
}