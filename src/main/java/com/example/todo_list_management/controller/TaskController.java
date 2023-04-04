package com.example.todo_list_management.controller;

import com.example.todo_list_management.model.entity.Category;
import com.example.todo_list_management.model.entity.Task;
import com.example.todo_list_management.model.request.TaskRequest;
import com.example.todo_list_management.model.response.BodyResponse;
import com.example.todo_list_management.model.response.ResponseClass;
import com.example.todo_list_management.model.response.TaskResponse;
import com.example.todo_list_management.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
@RestController
@RequestMapping("/api/v1/tasks")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private <T> ResponseEntity getBodyResponse(HttpStatus httpStatus, Boolean success, T payload) {
        BodyResponse<T> response = BodyResponse.<T>builder()
                .success(success)
                .date(new  Timestamp(System.currentTimeMillis()))
                .payload(payload)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    //Fetch all recorde task
    @GetMapping("")
    @Operation(summary = "Gets all tasks")
    public ResponseEntity<BodyResponse<List<Task>>> getAllTask() {
        return getBodyResponse(HttpStatus.OK, true, taskService.getAllTask());
    }

    @GetMapping("/{id}/users")
    @Operation(summary = "Get task by id for current user")
    public ResponseEntity<BodyResponse<Task>> getTaskByCurrentUser(Integer taskId) {
        return getBodyResponse(HttpStatus.OK, true, taskService.getTaskByCurrentUser(taskId));
    }

    //Fetch recorde by id
    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<BodyResponse<Task>>  getCategoryById(@PathVariable("id") Integer taskId){
        return getBodyResponse(HttpStatus.OK, true, taskService.getTaskById(taskId));
    }

    //Insert new Query
    @PostMapping("/users")
    @Operation(summary = "Add new tasks")
    public ResponseEntity<BodyResponse<Task>> insertNewTask(@RequestBody TaskRequest taskRequest){
        return getBodyResponse(HttpStatus.OK, true, taskService.getTaskById(taskService.insertNewTask(taskRequest)));
    }

    //Update query
    @PutMapping("/{id}/users")
    @Operation (summary = "Update task by id for current user")
    public ResponseEntity<BodyResponse<Task>> updateTask(@RequestBody TaskRequest taskRequest, @PathVariable("id") Integer taskId){
        return getBodyResponse(HttpStatus.OK, true, taskService.getTaskById(taskService.updateTask(taskRequest, taskId)));
    }

    @DeleteMapping("/{id}/users")
    @Operation (summary = "Delete task by id for current user")
    public ResponseEntity<BodyResponse<String>> deleteTaskById(@PathVariable("id") Integer taskId) {
        Integer id = taskService.deleteTaskById(taskId);
       return getBodyResponse(HttpStatus.OK, true, "Delete this task id " + id + " is successfully");
    }

    @GetMapping("/status/users")
    @Operation (summary = "Filter task by status for current user")
    public ResponseEntity<List<Task> > searchByStatus(@RequestParam("status") String status) {
        return getBodyResponse(HttpStatus.OK, true, taskService.searchByStatus(status));
    }

    @GetMapping("/users")
    @Operation (summary = "Get all tasks for current user")
    public ResponseEntity<BodyResponse<List<Task>>> getAllTaskByCurrentUser() {
        return getBodyResponse(HttpStatus.OK, true, taskService.getAllTaskByCurrentUser());
    }

}
