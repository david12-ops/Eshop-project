package com.example.service_interface;

import java.util.List;

import com.example.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser getUser(Integer id);

    void saveUser(AppUser user);

    void deleteUser(Integer id);

    List<AppUser> getAllUsers();
}
