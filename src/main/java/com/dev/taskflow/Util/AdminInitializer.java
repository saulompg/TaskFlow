package com.dev.taskflow.Util;

import com.dev.taskflow.Entity.User;
import com.dev.taskflow.Enums.UserRole;
import com.dev.taskflow.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) {
        String adminEmail = "admin@system.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User(
                    adminEmail,
                    passwordEncoder.encode("Admin@123"),
                    "Admin",
                    "System",
                    UserRole.ADMIN
            );
            userRepository.save(admin);
            System.out.println("✅ Admin inicial criado: " + adminEmail);
        }
    }
}
