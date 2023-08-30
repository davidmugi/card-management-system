package com.card.management.service;

import com.card.management.configuration.exception.BadRequestException;
import com.card.management.entity.User;
import com.card.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       try{
           final User user = userRepository.findUserByEmail(username)
                   .orElseThrow(() -> new UsernameNotFoundException("Invalid login request"));

           return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.isEnabled(),
                   true,true,true,getAuthorities());
       }catch (Exception ex){
           ex.getMessage();
       }
        final User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid login request"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.isEnabled(),
                true,true,true,getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

}
