package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

    public static final String NO_RECORDS_MSG_ERROR = "No records found for this id";
    @Autowired
    private UserRepository repository;

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    public UserServices(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username + "!");
        var user = repository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + "not found");
        }
    }
}
