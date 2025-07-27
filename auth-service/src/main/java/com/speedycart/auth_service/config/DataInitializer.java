package com.speedycart.auth_service.config;

import com.speedycart.auth_service.entity.Role;
import com.speedycart.auth_service.entity.User;
import com.speedycart.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        // Create admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@speedycart.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("System")
                    .lastName("Administrator")
                    .phoneNumber("+1234567890")
                    .roles(Set.of(Role.ADMIN))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            userRepository.save(admin);
            log.info("Created admin user: admin@speedycart.com");
        }

        // Create store manager user
        if (!userRepository.existsByUsername("storemanager")) {
            User storeManager = User.builder()
                    .username("storemanager")
                    .email("store@speedycart.com")
                    .password(passwordEncoder.encode("store123"))
                    .firstName("Store")
                    .lastName("Manager")
                    .phoneNumber("+1234567891")
                    .roles(Set.of(Role.STORE_MANAGER))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            userRepository.save(storeManager);
            log.info("Created store manager user: store@speedycart.com");
        }

        // Create delivery agent user
        if (!userRepository.existsByUsername("delivery")) {
            User deliveryAgent = User.builder()
                    .username("delivery")
                    .email("delivery@speedycart.com")
                    .password(passwordEncoder.encode("delivery123"))
                    .firstName("Delivery")
                    .lastName("Agent")
                    .phoneNumber("+1234567892")
                    .roles(Set.of(Role.DELIVERY_AGENT))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            userRepository.save(deliveryAgent);
            log.info("Created delivery agent user: delivery@speedycart.com");
        }

        // Create customer user
        if (!userRepository.existsByUsername("customer")) {
            User customer = User.builder()
                    .username("customer")
                    .email("customer@speedycart.com")
                    .password(passwordEncoder.encode("customer123"))
                    .firstName("John")
                    .lastName("Customer")
                    .phoneNumber("+1234567893")
                    .roles(Set.of(Role.CUSTOMER))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            userRepository.save(customer);
            log.info("Created customer user: customer@speedycart.com");
        }

        log.info("Data initialization completed successfully");
    }
}
