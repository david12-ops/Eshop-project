package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.RoleDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.MyUserDetail;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service_interface.RoleService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final ObjectMapper<Role, RoleDTO> roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ObjectMapper<Role, RoleDTO> roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO, MyUserDetail userDetail) {
        if (roleDTO == null) {
            throw new BadRequestException("Role must not be null");
        }

        roleDTO.setCreatedAt(Instant.now());
        roleDTO.setUpdatedAt(Instant.now());

        roleDTO.setCreatedBy(userDetail.getUserId());
        roleDTO.setUpdatedBy(userDetail.getUserId());

        roleDTO.setDeleted(false);
        roleDTO.setDeletedAt(null);

        Role savedRole = roleRepository.save(roleMapper.mapToEntity(roleDTO));

        return roleMapper.mapToDTO(savedRole);
    }

    @Override
    public RoleDTO findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).map(role -> roleMapper.mapToDTO(role))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    @Override
    public List<RoleDTO> getAllRoles(MyUserDetail userDetail) {
        return roleRepository.findAll().stream()
                .map(roleMapper::mapToDTO).toList();
    }

    // @Override
    // @Transactional
    // public RoleDTO getRoleWithUsers(Integer id) {
    // return roleRepository.findByIdWithUsers(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " +
    // id));
    // }

    // @Override
    // @Transactional
    // public RoleDTO getRoleWithPermissions(Integer id) {
    // return roleRepository.findByIdWithPermissions(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " +
    // id));
    // }

    // @Override
    // @Transactional
    // public RoleDTO getRoleWithUsersAndPermissions(Integer id) {
    // return roleRepository.findByIdWithUsersAndPermissions(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " +
    // id));
    // }

    @Override
    public RoleDTO getRoleById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return roleRepository.findById(id).map(role -> roleMapper.mapToDTO(role))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    // Soft deletion
    @Override
    public void deleteRoleById(Integer id, MyUserDetail userDetail) {
        Role roleToDelete = getRoleEntityById(id);

        if (roleToDelete.isDeleted())
            return;

        roleToDelete.setUpdatedAt(Instant.now());
        roleToDelete.setUpdatedBy(userDetail.getUserId());

        roleToDelete.setDeleted(true);
        roleToDelete.setDeletedAt(Instant.now());

        roleRepository.save(roleToDelete);
        // roleRepository.softDeleteById(existingRole.getRoleId());
    }

    @Override
    public RoleDTO editRole(Integer id, RoleDTO roleDTO, MyUserDetail userDetail) {
        if (roleDTO == null) {
            throw new BadRequestException("Role to edit must not be null");
        }

        Role roleToUpdate = getRoleEntityById(id);

        roleToUpdate.setRoleName(roleDTO.getRoleName());
        roleToUpdate.setRoleType(roleDTO.getRoleType());
        roleToUpdate.setRoleDescription(roleDTO.getRoleDescription());
        roleToUpdate.setUpdatedAt(Instant.now());
        roleToUpdate.setUpdatedBy(userDetail.getUserId());
        roleToUpdate.setDeleted(false);
        roleToUpdate.setDeletedAt(null);

        return roleMapper.mapToDTO(roleRepository.save(roleToUpdate));
    }

    private Role getRoleEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }
}
