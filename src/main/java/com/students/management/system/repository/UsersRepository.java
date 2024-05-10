package com.students.management.system.repository;

import com.students.management.system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE id = :userId")
    Users findByUserId(Long userId);

    Users findByIdAndIsDeletedFalse(Long id);

    Users findByEmail(String email);
}
