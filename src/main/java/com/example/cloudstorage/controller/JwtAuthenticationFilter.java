package com.example.cloudstorage.controller;

import com.example.cloudstorage.repository.TokenRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import com.example.cloudstorage.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "auth-token";
    private final JwtService jwtService;
    private final UserInfoRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserInfoRepository userRepository,
            TokenRepository tokenRepository
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Получаем токен из заголовка
        var authTokenFromHeader = request.getHeader(HEADER_NAME);
        if (authTokenFromHeader == null
                || authTokenFromHeader.isEmpty()
                || !StringUtils.startsWith(authTokenFromHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authTokenFromHeader.substring((BEARER_PREFIX.length()));

        if (!tokenRepository.isTokenValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        var username = jwtService.extractUserName(jwt);

        if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userRepository.getByLogin(username);

            // Если токен валиден, то аутентифицируем пользователя
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}