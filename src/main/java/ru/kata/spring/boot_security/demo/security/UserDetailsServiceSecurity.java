package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceSecurity implements UserDetailsService {

    private final UserServiceImpl userService;

    public UserDetailsServiceSecurity(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("there is no %s user", email));
        }
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles) {
        return roles
                .stream()
                .map(el -> new SimpleGrantedAuthority("ROLE_" + el.getName()))
                .collect(Collectors.toList());
    }
}
