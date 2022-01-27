package com.services.app.services;

import java.util.ArrayList;
import java.util.Collection;
import com.services.app.entities.UserEntity;
import com.services.app.repositories.IUserRepository;
import com.services.app.services.support.AuthUser;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenService implements UserDetailsService {

    private final IUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        System.out.println("-------------(((((" + username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("UserNotFound!");
        } else {
            log.info("User vas found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getEmployee().getRoles()
                .forEach(r -> {
                    authorities.add(new SimpleGrantedAuthority(r.getTitle()));
                });
        return new AuthUser(user.getUsername(), user.getPassword(), authorities, user.getId(),
                user.getEmployee().getFullName());
        // return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public UserEntity getUser(String username) {
        log.info("Fetching user");
        return userRepo.findByUsername(username);
    }

}
