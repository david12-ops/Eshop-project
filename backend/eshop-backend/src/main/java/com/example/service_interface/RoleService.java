package com.example.service_interface;

import com.example.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    void saveRole(Role role);

    Optional<Role> findById(Integer id);

    Role findByRoleName(String roleName);

    List<Role> findAll();

    void deleteById(Integer id);
}