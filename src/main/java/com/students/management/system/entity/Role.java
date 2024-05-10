package com.students.management.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.students.management.system.enums.Roles;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Roles roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges", joinColumns = {
            @JoinColumn(referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(referencedColumnName = "id")})
    private Set<Privilege> privileges;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @JsonIgnore
    private Set<Users> users;
}
