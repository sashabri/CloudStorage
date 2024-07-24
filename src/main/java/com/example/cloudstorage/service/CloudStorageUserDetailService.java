package com.example.cloudstorage.service;

import com.example.cloudstorage.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CloudStorageUserDetailService implements UserDetailsService {
    @Autowired
    UserInfoRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.getByLogin(username);
        if (user == null) throw new UsernameNotFoundException("User " + username + " not found");
        return user;
    }
}
