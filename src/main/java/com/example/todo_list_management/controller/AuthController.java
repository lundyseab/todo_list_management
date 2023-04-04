package com.example.todo_list_management.controller;

import com.example.todo_list_management.model.dto.UserAppDTO;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.AuthRequest;
import com.example.todo_list_management.model.request.UserAppRequest;
import com.example.todo_list_management.model.response.AuthResponse;
import com.example.todo_list_management.model.response.BodyResponse;
import com.example.todo_list_management.service.AuthService;
import com.example.todo_list_management.service.serviceImp.UserAppServiceImp;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final UserAppServiceImp userAppServiceImp;
    private final AuthService authService;

    private <T> ResponseEntity getBodyResponse(HttpStatus httpStatus, Boolean success, T payload) {
        BodyResponse<T> response = BodyResponse.<T>builder()
                .success(success)
                .date(new  Timestamp(System.currentTimeMillis()))
                .payload(payload)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserAppDTO> insertUser(@RequestBody UserAppRequest userAppRequest) {
        return getBodyResponse(HttpStatus.OK, true, userAppServiceImp.register(userAppRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return getBodyResponse(HttpStatus.OK, true, authService.authenticate(authRequest));
    }

}
