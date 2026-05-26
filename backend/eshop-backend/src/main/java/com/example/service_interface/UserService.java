package com.example.service_interface;

import java.util.List;

import com.example.dto.RegisterRequest;
import com.example.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    AppUser getUserById(Integer id);

    void saveUser(AppUser user);

    void editUser(Integer id, AppUser user);

    void deleteUserById(Integer id);

    List<AppUser> getAllUsers();

    void registerUser(RegisterRequest request);
}
