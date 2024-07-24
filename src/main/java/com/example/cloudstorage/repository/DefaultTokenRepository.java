package com.example.cloudstorage.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
public class DefaultTokenRepository implements TokenRepository {

    private final Collection<String> tokens = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void saveToken(String token) {
        if (token != null) {
            tokens.add(token);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        if (token == null) return false;
        return tokens.contains(token);
    }

    @Override
    public void invalidateToken(String token) {
        if (token != null) {
            tokens.remove(token);
        }
    }
}
