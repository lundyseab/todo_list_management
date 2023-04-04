package com.example.todo_list_management.service.serviceImp;

import com.example.todo_list_management.exception.*;
import com.example.todo_list_management.model.dto.UserAppDTO;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.UserAppRequest;
import com.example.todo_list_management.repository.UserAppRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAppServiceImp implements UserDetailsService {
    private final UserAppRepository userAppRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserApp userApp = userAppRepository.getUserByEmail(email);

        if(userApp == null) {
            throw new NotFoundExceptionHandler("User not found");
        }
        else if(email.isEmpty()) {
            throw new FieldEmptyExceptionHandler("Email field is empty");
        }
        else if(email.isBlank()) {
            throw new FieldBlankExceptionHandler("Email field is blank");
        }
        else if(!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-]+)(\\.[a-zA-Z]{2,5}){1,2}$")) {
            throw new NotValidValueExceptionHandler("Email not valid");
        }

        return userApp;
    }

    public UserAppDTO register(UserAppRequest userAppRequest) {

        String email = userAppRepository.getEmail(userAppRequest.getEmail());

        if(userAppRequest.getEmail().equalsIgnoreCase(email)) {
            throw new UserDuplicateExceptionHandler("User already registered");
        }
        else if(userAppRequest.getEmail().isEmpty()) {
            throw new FieldEmptyExceptionHandler("Email field is empty");
        }
        else if(userAppRequest.getEmail().isBlank()) {
            throw new FieldBlankExceptionHandler("Email field is blank");
        }
        else if(userAppRequest.getPassword().isEmpty()) {
            throw new FieldEmptyExceptionHandler("Password field is empty");
        }
        else if(userAppRequest.getPassword().isBlank()) {
            throw new FieldBlankExceptionHandler("Password field is blank");
        }
        else if(!userAppRequest.getEmail().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-]+)(\\.[a-zA-Z]{2,5}){1,2}$")) {
            throw new NotValidValueExceptionHandler("Email not valid");
        }
        else if(!userAppRequest.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new NotValidValueExceptionHandler("Password minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
        }

        userAppRequest.setPassword(passwordEncoder.encode(userAppRequest.getPassword()));
        UserApp userApp = userAppRepository.insertUser(userAppRequest);
        return mapper.map(userApp, UserAppDTO.class);
    }
}
