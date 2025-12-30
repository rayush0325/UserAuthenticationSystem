package com.learning.UserAuthenticationSystem.service;

import com.learning.UserAuthenticationSystem.model.User;
import com.learning.UserAuthenticationSystem.repository.UserRepository;
import com.learning.UserAuthenticationSystem.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager =authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> saveUser(User user) {
        try {


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> authenticateUser(User user) {
        String jwt = null;
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
            jwt = jwtUtils.generateToken(userDetails);

        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
