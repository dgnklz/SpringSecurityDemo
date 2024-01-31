package com.dgnklz.springsecurity.repository;

import com.dgnklz.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);
    boolean existsUserByEmail(String username);
}