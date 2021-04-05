package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Role;
import com.geekbrains.demoboot.entities.User;
import com.geekbrains.demoboot.repositories.RoleRepository;
import com.geekbrains.demoboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

public interface UserService extends UserDetailsService {
}
