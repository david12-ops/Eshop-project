package com.example.service_interface;

import com.example.dto.RoleDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface RoleService {

    RoleDTO saveRole(RoleDTO roleDTO, MyUserDetail userDetail);

    RoleDTO findByRoleName(String roleName);

    List<RoleDTO> getAllRoles(MyUserDetail userDetail);

    RoleDTO getRoleById(Integer id, MyUserDetail userDetail);

    void deleteRoleById(Integer id, MyUserDetail userDetail);

    RoleDTO editRole(Integer id, RoleDTO roleDTO, MyUserDetail userDetail);

    // RoleDTO getRoleWithUsers(Integer id);

    // RoleDTO getRoleWithPermissions(Integer id);

    // RoleDTO getRoleWithUsersAndPermissions(Integer id);
}