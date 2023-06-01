package com.example.restwithspringbootandjava.service;

import com.example.restwithspringbootandjava.repositories.UserRepository;
import com.example.restwithspringbootandjava.security.JwtTokenProvider;
import com.example.restwithspringbootandjava.vo.security.AccountCredentialsVO;
import com.example.restwithspringbootandjava.vo.security.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices  {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity SignIn(AccountCredentialsVO data) {
        try {
            var userName = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            var user = repository.findByUsername(userName);

            var tokenResponse = new TokenVO();
            if(user != null) {
                tokenResponse = jwtTokenProvider.createAccessToken(userName, user.getRoles());
            } else {
                throw new UsernameNotFoundException("User name " + userName + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}
