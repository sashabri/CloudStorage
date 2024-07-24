package com.example.cloudstorage.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users_info")
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column (name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserFileInfo> listFilesInfo;

    public UserInfo() {}

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<UserFileInfo> getListFilesInfo() {
        return listFilesInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setListFilesInfo(List<UserFileInfo> listFilesInfo) {
        this.listFilesInfo = listFilesInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
