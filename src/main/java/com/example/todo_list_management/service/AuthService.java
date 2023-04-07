package com.example.todo_list_management.service;

import com.example.todo_list_management.config.JwtAuthFilter;
import com.example.todo_list_management.config.JwtUtil;
import com.example.todo_list_management.exception.InvalidPasswordExceptionHandler;
import com.example.todo_list_management.exception.NotFoundExceptionHandler;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.AuthRequest;
import com.example.todo_list_management.model.response.AuthResponse;
import com.example.todo_list_management.repository.UserAppRepository;
import com.example.todo_list_management.service.serviceImp.UserAppServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserAppRepository userAppRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest authRequest) {

        String password = passwordEncoder.encode(authRequest.getPassword());
        UserApp getUser = userAppRepository.getUserByEmail(authRequest.getEmail());

        if(!password.equals(getUser.getPassword())) {
            throw new InvalidPasswordExceptionHandler("Invalid password");
        }

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
    }

}
