package com.students.management.system.service;

import com.students.management.system.entity.Privilege;
import com.students.management.system.entity.Role;
import com.students.management.system.enums.Privileges;
import com.students.management.system.enums.Roles;
import com.students.management.system.repository.PrivilegeRepository;
import com.students.management.system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleService {

    private  final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, PrivilegeRepository privilegeRepository){
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }


    public Role findRole(Roles roles) {
        return roleRepository.findByRoleName(roles);
    }

    public void createSuperAdminRole() {
        List<Privilege> privilegeList = privilegeRepository.findAll();
        Role role = new Role();
        role.setRoleName(Roles.PRINCIPLE);
        role.setPrivileges((new HashSet<>(privilegeList)));
        roleRepository.save(role);
    }

    public void createCustomerRole() {
        Role role = new Role();
        role.setRoleName(Roles.TEACHER);
        roleRepository.save(role);
    }

    public void createHelperRole() {
        Role role = new Role();
        role.setRoleName(Roles.STUDENT);
        roleRepository.save(role);
    }
}