package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.AppUser;
import com.example.model.MyUserDetail;
import com.example.repository.UserRepository;
import com.example.service_interface.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            EntityManager entityManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    public AppUser getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public void saveUser(AppUser user) {
        if (user == null)
            return;

        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        if (id == null)
            return;

        userRepository.findById(id).ifPresent(user -> userRepository.deleteById(id));
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(userName);
        return new MyUserDetail(
                user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName)));
    }
}
