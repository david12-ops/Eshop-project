package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service_interface.RoleService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(Role role) {
        if (role == null)
            return;

        roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null)
            return;

        roleRepository.findById(id).ifPresent(role -> roleRepository.deleteById(id));
    }

    @Override
    @Transactional
    public Role getRoleWithUsers(Integer id) {

        return roleRepository.findByIdWithUsers(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    @Transactional
    public Role getRoleWithPermissions(Integer id) {

        return roleRepository.findByIdWithPermissions(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    @Transactional
    public Role getRoleWithUsersAndPermissions(Integer id) {

        return roleRepository.findByIdWithUsersAndPermissions(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
