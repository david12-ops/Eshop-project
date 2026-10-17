package com.example.service_interface;

import com.example.dto.AppUserDTO;
import com.example.dto.RegisterRequestDTO;
import com.example.model.MyUserDetail;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUserDTO getAppUserById(Integer id, MyUserDetail userDetail);

    AppUserDTO saveAppUser(AppUserDTO appUserDTO, MyUserDetail userDetail);

    AppUserDTO editAppUser(Integer id, AppUserDTO appUserDTO, MyUserDetail userDetail);

    void deleteAppUserById(Integer id, MyUserDetail userDetail);

    List<AppUserDTO> getAppUsers(MyUserDetail userDetail);

    void registerUser(RegisterRequestDTO request);
}
