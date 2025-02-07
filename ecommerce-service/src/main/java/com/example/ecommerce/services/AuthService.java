package com.example.ecommerce.services;

import com.example.common.dto.users.AuthResponse;
import com.example.common.dto.users.LoginRequest;
import com.example.common.dto.users.RegisterRequest;
import com.example.common.dto.users.UserDTO;
import com.example.common.models.User;
import com.example.ecommerce.repositories.UserRepo;
import com.example.ecommerce.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encode password
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoles(Collections.singleton("ROLE_USER")); // Default role

        User savedUser = userRepository.save(user);

        // Convert to UserDetails for JWT generation
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String jwt = jwtService.generateToken(userDetails);

        return createAuthResponse(savedUser, jwt);
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // If authentication successful, generate token
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwt = jwtService.generateToken(userDetails);

        return createAuthResponse(user, jwt);
    }

    private AuthResponse createAuthResponse(User user, String jwt) {
        AuthResponse response = new AuthResponse();
        response.setToken(jwt);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRoles(user.getRoles());

        response.setUser(userDTO);
        return response;
    }
}
