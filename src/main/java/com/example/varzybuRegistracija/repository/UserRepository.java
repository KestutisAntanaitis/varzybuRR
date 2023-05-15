package com.example.varzybuRegistracija.repository;

import com.example.varzybuRegistracija.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAllByRoles_Name(String role);

    Long countAllByRoles_Name(String roleName);
}
