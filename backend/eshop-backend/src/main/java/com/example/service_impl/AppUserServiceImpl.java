package com.example.service_impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.AppUserDTO;
import com.example.dto.RegisterRequestDTO;
import com.example.dto.RoleDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.AppUser;
import com.example.model.MyUserDetail;
import com.example.model.Role;
import com.example.model.enums.RoleType;
import com.example.repository.UserRepository;
import com.example.service_interface.AppUserService;
import com.example.service_interface.RoleService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper<AppUser, AppUserDTO> appUserMapper;
    private final ObjectMapper<Role, RoleDTO> roleMapper;

    public AppUserServiceImpl(UserRepository userRepository, RoleService roleService,
            PasswordEncoder passwordEncoder, ObjectMapper<AppUser, AppUserDTO> appUserMapper,
            ObjectMapper<Role, RoleDTO> roleMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.appUserMapper = appUserMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public AppUserDTO getAppUserById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return userRepository.findById(id).map(appUser -> appUserMapper.mapToDTO(appUser))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public AppUserDTO saveAppUser(AppUserDTO appUserDTO, MyUserDetail userDetail) {
        if (appUserDTO == null) {
            throw new BadRequestException("User must not be null");
        }

        appUserDTO.setCreatedAt(Instant.now());
        appUserDTO.setUpdatedAt(Instant.now());

        appUserDTO.setCreatedBy(userDetail.getUserId());
        appUserDTO.setUpdatedBy(userDetail.getUserId());

        appUserDTO.setDeleted(false);
        appUserDTO.setDeletedAt(null);

        AppUser savedAppUser = userRepository.save(appUserMapper.mapToEntity(appUserDTO));

        return appUserMapper.mapToDTO(savedAppUser);
    }

    // Soft deletion
    @Override
    public void deleteAppUserById(Integer id, MyUserDetail userDetail) {
        AppUser appUserToDelete = getAppUserEntityById(id);

        if (appUserToDelete.isDeleted())
            return;

        appUserToDelete.setUpdatedAt(Instant.now());
        appUserToDelete.setUpdatedBy(userDetail.getUserId());

        appUserToDelete.setDeleted(true);
        appUserToDelete.setDeletedAt(Instant.now());

        userRepository.save(appUserToDelete);
        // userRepository.softDeleteById(existingUserDTO.getAppUserId());
    }

    @Override
    public List<AppUserDTO> getAppUsers(MyUserDetail userDetail) {
        return userRepository.findAll().stream()
                .map(appUserMapper::mapToDTO).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        Optional<AppUser> user = userRepository.findByUsername(userName);
        return new MyUserDetail(
                user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName)));
    }

    @Override
    public AppUserDTO editAppUser(Integer id, AppUserDTO appUserDTO, MyUserDetail userDetail) {
        if (appUserDTO == null) {
            throw new BadRequestException("User to edit must not be null");
        }

        AppUser appUserToEdit = getAppUserEntityById(id);

        appUserToEdit.setUsername(appUserDTO.getUsername());
        appUserToEdit.setEmail(appUserDTO.getEmail());
        appUserToEdit.setUpdatedAt(Instant.now());
        appUserToEdit.setUpdatedBy(userDetail.getUserId());
        appUserToEdit.setDeleted(false);
        appUserToEdit.setDeletedAt(null);

        if (appUserDTO.getPassword() != null && !appUserDTO.getPassword().isEmpty()) {
            appUserToEdit.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        }

        if (appUserDTO.getRoleDTO() != null
                && appUserDTO.getRoleDTO().getRoleId() != null) {

            Role role = roleMapper.mapToEntity(roleService.getRoleById(
                    appUserDTO.getRoleDTO().getRoleId(), userDetail));

            appUserToEdit.setRole(role);
        } else {

            appUserToEdit.setRole(null);
        }

        return appUserMapper.mapToDTO(userRepository.save(appUserToEdit));
    }

    @Override
    public void registerUser(RegisterRequestDTO request) {
        AppUser user = new AppUser();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role customerRole = roleMapper.mapToEntity(roleService.findByRoleName(RoleType.CUSTOMER.toString()));

        user.setRole(customerRole);

        userRepository.save(user);
    }

    private AppUser getAppUserEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
