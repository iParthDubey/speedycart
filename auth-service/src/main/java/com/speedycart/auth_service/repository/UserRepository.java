package com.speedycart.auth_service.repository;

import com.speedycart.auth_service.entity.Role;
import com.speedycart.auth_service.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findByRole(@Param("role") Role role);

    @Query("SELECT u FROM User u WHERE u.enabled = :enabled")
    List<User> findByEnabled(@Param("enabled") boolean enabled);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% OR u.email LIKE %:email%")
    List<User> findByUsernameContainingOrEmailContaining(
            @Param("username") String username,
            @Param("email") String email
    );

    boolean existsByUsername(String username);
}