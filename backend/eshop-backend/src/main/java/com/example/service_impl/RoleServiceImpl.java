package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.BadRequestException;
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
    public Role saveRole(Role role) {
        if (role == null) {
            throw new BadRequestException("Role must not be null");
        }

        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    @Override
    public List<Role> getAllRoles() {
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
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    // Soft deletion
    @Override
    public void deleteRoleById(Integer id) {
        Role existingRole = getRoleById(id);

        if (existingRole.getDeleted())
            return;

        roleRepository.softDeleteById(existingRole.getRoleId());
    }

    @Override
    public Role editRole(Integer id, Role role) {
        if (role == null) {
            throw new BadRequestException("Role to edit must not be null");
        }

        Role existingRole = getRoleById(id);

        existingRole.setRoleName(role.getRoleName());
        existingRole.setRoleType(role.getRoleType());
        existingRole.setRoleDescription(role.getRoleDescription());

        return roleRepository.save(existingRole);
    }
}
