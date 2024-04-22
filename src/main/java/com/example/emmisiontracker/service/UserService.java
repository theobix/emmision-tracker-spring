package com.example.emmisiontracker.service;

import com.example.emmisiontracker.constants.UserRole;
import com.example.emmisiontracker.domain.user.CredentialsInputDto;
import com.example.emmisiontracker.domain.user.User;
import com.example.emmisiontracker.domain.user.UserDto;
import com.example.emmisiontracker.exception.EmailExistsExceptionCustom;
import com.example.emmisiontracker.exception.EmailFormatExceptionCustom;
import com.example.emmisiontracker.repository.UserRepository;
import com.example.emmisiontracker.util.EmailUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public User registerNewUser(UserDto userDto) throws EmailExistsExceptionCustom, EmailFormatExceptionCustom {

        if (!EmailUtil.isValidEmail(userDto.getEmail()))
            throw new EmailFormatExceptionCustom(userDto.getEmail());

        if (emailExists(userDto.getEmail()))
            throw new EmailExistsExceptionCustom(userDto.getEmail());

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


        if (authentication.isAuthenticated()) {
            return jwtService.generate(credentialsInputDto.getEmail());
        }

        return null;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public User getUserProfile() {
        return getUserFromContext();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public User updateUserProfile(UserDto userDto) throws IOException, EmailFormatExceptionCustom {

        if (!EmailUtil.isValidEmail(userDto.getEmail()))
            throw new EmailFormatExceptionCustom(userDto.getEmail());

        User user = getUserFromContext();

        user.setName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setProfilePictureURL(userDto.getProfilePictureURL());

        userRepository.save(user);

        return user;
    }

}
