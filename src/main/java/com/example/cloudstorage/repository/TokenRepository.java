package com.example.cloudstorage.repository;

public interface TokenRepository {
    void saveToken(String token);
    boolean isTokenValid(String token);
    void invalidateToken(String token);
}
