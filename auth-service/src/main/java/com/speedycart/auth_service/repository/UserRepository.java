package com.speedycart.auth_service.repository;

import com.speedycart.auth_service.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);

    Optional<Object> findByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email);
}