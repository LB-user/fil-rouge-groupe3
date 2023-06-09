package com.gestionSalleCefim.Group3.security.config;

import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Re définition du [UserDetailsService] pour le mécanisme d'authentification
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> account = userRepository.findByEmail(username);
        if (account.isPresent()) {
            return account.get();
        }
        throw new UsernameNotFoundException("the username $username doesn't exist");
    }
}