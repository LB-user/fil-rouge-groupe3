package com.gestionSalleCefim.Group3.security.config;

import com.gestionSalleCefim.Group3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    // règle de sécurité
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.formLogin();
        return http.build();
    }
// authentification avec user et mdp
    @Autowired
    public void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                com.gestionSalleCefim.Group3.entities.User user = userService.loadUserByEmail(email);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
                return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);

            }
        });
    }

}
