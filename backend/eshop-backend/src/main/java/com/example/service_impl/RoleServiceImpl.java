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
    @Transactional
    public Role getRoleWithUsers(Integer id) {
        return roleRepository.findByIdWithUsers(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    @Override
    @Transactional
    public Role getRoleWithPermissions(Integer id) {
        return roleRepository.findByIdWithPermissions(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    @Override
    @Transactional
    public Role getRoleWithUsersAndPermissions(Integer id) {
        return roleRepository.findByIdWithUsersAndPermissions(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    @Override
    public void deleteRoleById(Integer id) {
        if (id == null)
            return;

        // zatím ne
        roleRepository.findById(id).ifPresent(role -> {
            if (role.getDeleted())
                return; // tady jen soft kvůli klíčům

            roleRepository.softDeleteById(id);
        });
    }

    @Override
    public void editRole(Integer id, Role role) {
        if (id == null || role == null)
            return;

        roleRepository.findById(id).ifPresent(existingRole -> {

            existingRole.setRoleName(role.getRoleName());
            existingRole.setRoleType(role.getRoleType());
            existingRole.setRoleDescription(role.getRoleDescription());

            roleRepository.save(existingRole);
        });
    }
}
