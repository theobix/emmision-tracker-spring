package com.example.emmisiontracker.service;

import com.example.emmisiontracker.constants.UserRole;
import com.example.emmisiontracker.domain.user.CredentialsInputDto;
import com.example.emmisiontracker.domain.user.User;
import com.example.emmisiontracker.domain.user.UserDto;
import com.example.emmisiontracker.exception.EmailExistsException;
import com.example.emmisiontracker.exception.EmailFormatException;
import com.example.emmisiontracker.repository.UserRepository;
import com.example.emmisiontracker.util.EmailUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public User registerNewUser(UserDto userDto) throws EmailExistsException, EmailFormatException {

        if (!EmailUtil.isValidEmail(userDto.getEmail()))
            throw new EmailFormatException(userDto.getEmail());

        if (emailExists(userDto.getEmail()))
            throw new EmailExistsException(userDto.getEmail());

        User user = User.builder()
                .name(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(UserRole.USER)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public Map<String, String> login(CredentialsInputDto credentialsInputDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsInputDto.getEmail(), credentialsInputDto.getPassword())
        );

        logger.debug("Authenticated: {}", authentication.isAuthenticated());

        if (authentication.isAuthenticated()) {
            return jwtService.generate(credentialsInputDto.getEmail());
        }

        return null;
    }

}
