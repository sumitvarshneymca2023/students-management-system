package com.students.management.system.security;


import com.students.management.system.entity.Role;
import com.students.management.system.entity.Users;
import com.students.management.system.exception.GenericNotFoundException;
import com.students.management.system.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    Users user;

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        user = usersRepository.findByIdAndIsDeletedFalse(Long.valueOf(userName));
        if (user != null) {
            return new User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
        }
        throw new GenericNotFoundException("User not found");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()));
            role.getPrivileges().stream()
                    .map(privilege -> new SimpleGrantedAuthority(privilege.getName().name())).forEach(authorities::add);
        });
        return authorities;
    }

}
