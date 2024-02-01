package com.dgnklz.springsecurity.business.concretes;

import com.dgnklz.springsecurity.model.UserDetailsImpl;
import com.dgnklz.springsecurity.model.entity.User;
import com.dgnklz.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsManager implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkIfUserNotExistByEmail(username);
        User user = repository.findByEmail(username);
        return UserDetailsImpl.build(user);
    }

    private void checkIfUserNotExistByEmail(String username) {
        if (!repository.existsByEmail(username)) {
            throw new UsernameNotFoundException("User not found with: " + username);
        }
    }
}
