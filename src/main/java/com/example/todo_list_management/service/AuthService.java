package com.example.todo_list_management.service;

import com.example.todo_list_management.config.JwtAuthFilter;
import com.example.todo_list_management.config.JwtUtil;
import com.example.todo_list_management.exception.*;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.AuthRequest;
import com.example.todo_list_management.model.response.AuthResponse;
import com.example.todo_list_management.repository.UserAppRepository;
import com.example.todo_list_management.service.serviceImp.UserAppServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserAppRepository userAppRepository;

    public AuthResponse authenticate(AuthRequest authRequest) {

        UserApp userApp = userAppRepository.getUserByEmail(authRequest.getEmail());

        if(userApp == null) {
            throw new NotFoundExceptionHandler("User not found");
        }
        else if(authRequest.getEmail().isEmpty()) {
            throw new FieldEmptyExceptionHandler("Email field is empty");
        }
        else if(authRequest.getEmail().isBlank()) {
            throw new FieldBlankExceptionHandler("Email field is blank");
        }
        else if(!authRequest.getEmail().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-]+)(\\.[a-zA-Z]{2,5}){1,2}$")) {
            throw new NotValidValueExceptionHandler("Email not valid");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            var user = userAppRepository.getUserByEmail(authRequest.getEmail());

            var jwtToken = jwtUtil.generateToken(user);
            return AuthResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .token(jwtToken)
                    .build();

        } catch (Exception e) {
            throw new InvalidPasswordExceptionHandler("Invalid password");
        }
    }

}
