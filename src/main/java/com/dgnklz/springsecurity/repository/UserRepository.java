package com.dgnklz.springsecurity.repository;

import com.dgnklz.springsecurity.model.entity.User;
import com.dgnklz.springsecurity.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    UserRole findByRole(String role);
}
