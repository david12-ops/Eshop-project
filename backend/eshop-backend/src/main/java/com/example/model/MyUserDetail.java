package com.example.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetail implements UserDetails {

    private AppUser user;

    public MyUserDetail(AppUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        // ROLE
        authorities.add(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().getRoleType().name()));

        // PERMISSIONS
        for (AppPermission permission : user.getRole().getPermissions()) {

            String authority = permission.getResourceType().name()
                    + "_"
                    + permission.getOperationType().name();

            authorities.add(
                    new SimpleGrantedAuthority(authority));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Integer getUserId() {
        return user.getId();
    }
}