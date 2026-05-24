package com.example.service_interface;

import com.example.model.Role;

import java.util.List;

public interface RoleService {

    void saveRole(Role role);

    Role findByRoleName(String roleName);

    List<Role> findAll();

    void deleteById(Integer id);

    Role getRoleWithUsers(Integer id);

    Role getRoleWithPermissions(Integer id);

    Role getRoleWithUsersAndPermissions(Integer id);
}