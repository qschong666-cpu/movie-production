package cinema_backend.api_movie_system.service.impl;
import cinema_backend.api_movie_system.models.*;
import cinema_backend.api_movie_system.repository.*;
import cinema_backend.api_movie_system.repository.RoleRepository;
import cinema_backend.api_movie_system.repository.UserRepository;
import cinema_backend.api_movie_system.dto.CreateUserRequest;
import java.time.LocalDateTime;
import java.util.UUID;

import cinema_backend.api_movie_system.dto.CreateUserRequest;
import cinema_backend.api_movie_system.service.EmailService;
import cinema_backend.api_movie_system.service.UserService;
import jakarta.transaction.Transactional;

public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService; // Injected via Interface contract

    public UserServiceimpl(UserRepository userRepository, 
                           RoleRepository roleRepository, 
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest dto) {
        if (userRepository.existsByEmail(dto.getCompanyEmail())) {
            throw new IllegalArgumentException("Company email already exists.");
        }

        // 1. Fetch Role from database
        Role role = roleRepository.findByCode(dto.getRoleCode())
                .orElseThrow(() -> new IllegalArgumentException("Role code not found: " + dto.getRoleCode()));

        // 2. Map DTO to User entity & attach reset token directly
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getCompanyEmail());
        user.setRole(role);
        user.setStatus(false);


        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpiryDate(LocalDateTime.now().plusHours(24));

        userRepository.save(user);

        // 3. Delegate email delivery using the EmailService interface
        emailService.sendOnboardingEmail(dto.getPersonalEmail(), token);
    }
}
