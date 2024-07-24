package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ErrorResponse errorResponse;
        if (authException instanceof BadCredentialsException) {
            errorResponse = new ErrorResponse("Bad credentials", HttpStatus.BAD_REQUEST.value());
        } else {
            errorResponse = new ErrorResponse("Unauthorized error", HttpStatus.UNAUTHORIZED.value());
        }
        response.setStatus(errorResponse.getId());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, errorResponse);
        responseStream.flush();
    }
}