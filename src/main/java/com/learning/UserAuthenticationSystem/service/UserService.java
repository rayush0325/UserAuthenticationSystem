package com.learning.UserAuthenticationSystem.service;

import com.learning.UserAuthenticationSystem.dtos.LoginRequest;
import com.learning.UserAuthenticationSystem.dtos.LoginResponse;
import com.learning.UserAuthenticationSystem.dtos.RegisterRequest;
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
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager =authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> saveUser(RegisterRequest registerRequest) {
        try {
        User user = userRepository.findByUsername(registerRequest.getUsername());
        if(user != null){
            throw new RuntimeException("username already present");
        }
        user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        userRepository.save(user);

        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        String jwt = null;
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
            jwt = jwtUtils.generateToken(userDetails);

        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new LoginResponse(jwt), HttpStatus.OK);
    }
}
