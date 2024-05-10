package com.students.management.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    @JsonIgnore
    private String password;
    @ColumnDefault("true")
    private boolean isActive;
    private String pictureUrl;
    private boolean isDeleted;
    private String address;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(referencedColumnName = "id")})
    private Set<Role> roles;

}
