package com.example.suka_bapak.service.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.suka_bapak.entity.UserInfoEntity;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfoEntity userInfo) {
        this.username = userInfo.getName();
        this.password = userInfo.getPassword();
        this.authorities = List.of(userInfo.getRoles().split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

     @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
