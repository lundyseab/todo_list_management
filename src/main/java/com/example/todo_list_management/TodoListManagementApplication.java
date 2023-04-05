package com.example.todo_list_management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(title = "To-Do List HRD", version = "v1", description = "A to-do list is a simple and effective tool for keeping track or things you need to do")
)

@CrossOrigin()
public class TodoListManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListManagementApplication.class, args);
    }

}
