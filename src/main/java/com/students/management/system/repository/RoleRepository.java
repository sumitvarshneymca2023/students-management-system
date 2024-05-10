package com.students.management.system.repository;

import com.students.management.system.entity.Role;
import com.students.management.system.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(Roles roleName);
}
