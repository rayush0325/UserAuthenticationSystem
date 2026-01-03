package com.learning.UserAuthenticationSystem.service;

import com.learning.UserAuthenticationSystem.model.User;
import com.learning.UserAuthenticationSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceimpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserDetails userDetails = UserDetailsImpl.build(user);
        return userDetails;
    }
}
