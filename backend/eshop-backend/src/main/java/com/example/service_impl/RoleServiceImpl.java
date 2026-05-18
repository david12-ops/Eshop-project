package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service_interface.RoleService;

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
    public Optional<Role> findById(Integer id) {
        if (id == null)
            return null;

        return roleRepository.findById(id);
    }

    @Override
    public Role findByRoleName(String roleName) {
        if (roleName == null)
            return null;

        return roleRepository.findByRoleName(roleName);
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
}
