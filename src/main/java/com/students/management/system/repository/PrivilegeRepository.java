package com.students.management.system.repository;

import com.students.management.system.entity.Privilege;
import com.students.management.system.enums.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query("select p from Privilege p where p.name in ?1")
    List<Privilege> findByNameIn(Collection<Privileges> names);
}
