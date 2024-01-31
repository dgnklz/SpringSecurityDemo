package com.dgnklz.springsecurity.business;

import com.dgnklz.springsecurity.exception.BusinessException;
import com.dgnklz.springsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserManager implements UserService, UserDetailsService {
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkIfUserExistByUsername(username);
        return repository.findByEmail(username);
    }

    /// Domain Rules \\\

    private void checkIfUserExistByUsername(String username){
        if(!repository.existsUserByEmail(username)){
            throw new BusinessException("User not found");
        }
    }
}
