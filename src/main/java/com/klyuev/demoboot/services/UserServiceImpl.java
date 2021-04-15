package com.klyuev.demoboot.services;

import com.klyuev.demoboot.entities.Role;
import com.klyuev.demoboot.entities.User;
import com.klyuev.demoboot.repositories.RoleRepository;
import com.klyuev.demoboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) { this.roleRepository = roleRepository;}

    @Transactional
    public User findUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) {
        User user = findUserByUsername(username);
        if(user == null) {
            System.out.println("Пользователь не найден!");
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthority(user.getRoles()));
    }

    public void addUser(User user) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findOneByName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
