package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.RegisterRequestDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.AppUser;
import com.example.model.MyUserDetail;
import com.example.model.Role;
import com.example.model.enums.RoleType;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service_interface.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser getAppUserById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public AppUser saveAppUser(AppUser user) {
        if (user == null) {
            throw new BadRequestException("User must not be null");
        }

        return userRepository.save(user);
    }

    // Soft deletion
    @Override
    public void deleteAppUserById(Integer id) {
        AppUser existingUser = getAppUserById(id);

        if (existingUser.getDeleted())
            return;

        userRepository.softDeleteById(existingUser.getAppUserId());
    }

    @Override
    public List<AppUser> getAppUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        Optional<AppUser> user = userRepository.findByUsername(userName);
        return new MyUserDetail(
                user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName)));
    }

    @Override
    public AppUser editAppUser(Integer id, AppUser user) {
        if (user == null) {
            throw new BadRequestException("User to edit must not be null");
        }

        AppUser existingUser = getAppUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void registerUser(RegisterRequestDTO request) {
        AppUser user = new AppUser();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role customerRole = roleRepository.findByRoleType(RoleType.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: CUSTOMER"));

        user.setRole(customerRole);

        userRepository.save(user);
    }
}
