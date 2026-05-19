package com.example;

import java.time.OffsetDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.model.AppPermission;
import com.example.model.AppUser;
import com.example.model.Role;
import com.example.model.enums.OperationType;
import com.example.model.enums.ResourceType;
import com.example.model.enums.RoleType;
import com.example.service_interface.AppPermissionService;
import com.example.service_interface.RoleService;
import com.example.service_interface.UserService;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleService roleService, UserService userService,
            AppPermissionService appPermissionService) {
        return args -> {
            Role role = new Role();
            role.setRoleName("System Administrator");
            role.setRoleType(RoleType.ADMIN);
            role.setRoleDescription("System administrator");
            role.setCreatedAt(OffsetDateTime.now());
            role.setUpdatedAt(OffsetDateTime.now());
            role.setCreatedBy(null);
            role.setUpdatedBy(null);

            roleService.saveRole(role);

            AppUser user = new AppUser();
            user.setUsername("admin2");
            user.setEmail("admin2@example.com");
            user.setPassword("admin2");
            user.setRole(role);
            user.setCreatedAt(OffsetDateTime.now());
            user.setUpdatedAt(OffsetDateTime.now());
            user.setCreatedBy(null);
            user.setUpdatedBy(null);

            userService.saveUser(user);

            AppPermission permission = new AppPermission();

            permission.setRole(role);
            permission.setResourceType(ResourceType.APP_USERS);
            permission.setOperationType(OperationType.CREATE);

            permission.setCreatedAt(OffsetDateTime.now());
            permission.setUpdatedAt(OffsetDateTime.now());

            permission.setCreatedBy(user.getId());
            permission.setUpdatedBy(user.getId());

            appPermissionService.savePermission(permission);
        };
    }
}