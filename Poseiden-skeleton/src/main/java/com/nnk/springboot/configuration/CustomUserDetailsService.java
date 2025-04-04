package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Custom implementation of UserDetailsService to load user-specific date.
 * Used by Spring Security to retrieve user details for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    /* VARIABLES */
    private final UserService userService;
    private final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    /* CONSTRUCTOR */
    public CustomUserDetailsService(UserService userService) { this.userService = userService; }

    /* METHODS */

    /**
     * Loads user-specific data for authentication
     * @param username the username of the user to load.
     * @return a UserDetails object containing user data.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.fine("Loading user by username: " + username);
        User user = userService.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        logger.info("User loaded: " + user.getUsername());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    /**
     * Retrieves the authorities granted to the user based on their role.
     * @param role the role of the user.
     * @return a list of GrantedAuthority objects representing the user's authorities
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        logger.fine("Retrieving authorities for role: " + role);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        logger.info("Authorities retrieved, found: " + authorities.size());
        return authorities;
    }
}
