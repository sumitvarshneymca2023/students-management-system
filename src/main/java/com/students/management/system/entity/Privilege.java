package com.students.management.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.students.management.system.enums.Privileges;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "privileges", indexes = { @Index(name = "idx_privileges_name", columnList = "name") })
public class Privilege extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Privileges name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "privileges")
    @JsonIgnore
    private Set<Role> roles;

}
