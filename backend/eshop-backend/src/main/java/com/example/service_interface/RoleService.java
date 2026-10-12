package com.example.service_interface;

import com.example.model.Role;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role);

    Role findByRoleName(String roleName);

    List<Role> getAllRoles();

    Role getRoleById(Integer id);

    void deleteRoleById(Integer id);

    Role editRole(Integer id, Role role);

    Role getRoleWithUsers(Integer id);

    Role getRoleWithPermissions(Integer id);

    Role getRoleWithUsersAndPermissions(Integer id);
}