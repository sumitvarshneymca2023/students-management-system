package com.students.management.system.config;

import com.students.management.system.constants.MessageConstants;
import com.students.management.system.entity.Privilege;
import com.students.management.system.entity.Role;
import com.students.management.system.entity.Users;
import com.students.management.system.enums.Privileges;
import com.students.management.system.enums.Roles;
import com.students.management.system.repository.PrivilegeRepository;
import com.students.management.system.repository.RoleRepository;
import com.students.management.system.repository.UsersRepository;
import com.students.management.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrivilegeRepository privilegeRepository;
    private final RoleService roleService;

    @Autowired
    public Bootstrap(RoleRepository roleRepository,
                     UsersRepository usersRepository,
                     PasswordEncoder passwordEncoder,
                     PrivilegeRepository privilegeRepository,
                     RoleService roleService){
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.privilegeRepository = privilegeRepository;
        this.roleService = roleService;
    }
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(MessageConstants.APPLICATION_EVENT_METHOD);
        try {
            createPrivilegesIfNotExist();
            createDefaultRolesIfNotExist();
            createSuperAdminIfNotExist();
        } catch (Exception e) {
            log.error(MessageConstants.APPLICATION_EVENT_ERROR, e);
        }
    }

    private void createPrivilegesIfNotExist() {
        log.info(MessageConstants.DEFAULT_PRIVILEGES_CREATE);
        List<Privilege> privilegeList = privilegeRepository.findAll();
        List<Privilege> privileges = new ArrayList<>();
        if (privilegeList.isEmpty()) {
            for (Privileges privilegesName : Privileges.values()) {
                Privilege privilege = new Privilege();
                privilege.setName(privilegesName);
                privileges.add(privilege);
            }
            privilegeRepository.saveAll(privileges);
        }
    }

    public void createDefaultRolesIfNotExist() {
        log.info(MessageConstants.DEFAULT_ROLES_CREATE);
        try {
            List<Role> existingRoles = roleRepository.findAll();
            if (existingRoles.isEmpty()) {
                roleService.createSuperAdminRole();
                roleService.createHelperRole();
                roleService.createCustomerRole();
            }
        } catch (Exception e) {
            log.error(MessageConstants.DEFAULT_ROLES_CREATE_ERROR, e);
        }
    }

    private void createSuperAdminIfNotExist() {
        log.info(MessageConstants.SUPER_ADMIN_CREATE);
        try {
            Users adminUser = usersRepository.findByEmail(adminUsername);
            if (adminUser == null) {
                Users user = new Users();
                user.setFirstName("Sumit");
                user.setLastName("Varshney");
                user.setEmail(adminUsername);
                user.setActive(true);
                user.setPassword(passwordEncoder.encode(adminPassword));
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByRoleName(Roles.PRINCIPLE));
                user.setRoles(roles);
                usersRepository.save(user);
            }
        } catch (Exception e) {
            log.error(MessageConstants.SUPER_ADMIN_CREATE_ERROR, e);
        }
    }

}

