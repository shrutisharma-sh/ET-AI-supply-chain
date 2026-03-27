package com.shruti.supply_chain.services;


import com.shruti.supply_chain.dto.AuthResponse;
import com.shruti.supply_chain.dto.LoginRequest;
import com.shruti.supply_chain.dto.RegisterRequest;
import com.shruti.supply_chain.model.Role;
import com.shruti.supply_chain.model.SupplierProfile;
import com.shruti.supply_chain.model.User;
import com.shruti.supply_chain.repository.SupplierProfileRepository;
import com.shruti.supply_chain.repository.UserRepository;
import com.shruti.supply_chain.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final SupplierProfileRepository supplierProfileRepository;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);  //


        if (savedUser.getRole() == Role.SUPPLIER) {

            if (request.getCompanyName() == null || request.getCompanyName().isBlank()) {
                throw new RuntimeException("Company name is required for supplier");
            }
            SupplierProfile supplierProfile = SupplierProfile.builder()
                    .user(savedUser)
                    .companyName(request.getCompanyName())
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();

            supplierProfileRepository.save(supplierProfile);
        }

        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());


        return new AuthResponse(token);
    }
    // LOGIN
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token);
    }
}
