package com.example.biblioteca.repository;

import com.example.biblioteca.model.User;
import com.example.biblioteca.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.roles where u.username = :username")
    User findByUsernameFetchRoles(@Param("username") String username);
}
