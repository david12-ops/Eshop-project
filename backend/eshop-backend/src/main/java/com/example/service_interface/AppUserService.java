package com.example.service_interface;

import java.util.List;

import com.example.dto.RegisterRequest;
import com.example.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUser getAppUserById(Integer id);

    AppUser saveAppUser(AppUser user);

    AppUser editAppUser(Integer id, AppUser user);

    void deleteAppUserById(Integer id);

    List<AppUser> getAppUsers();

    void registerUser(RegisterRequest request);
}
