package com.example.todo_list_management.service.serviceImp;

import com.example.todo_list_management.exception.EmptyDataExceptionHandler;
import com.example.todo_list_management.exception.FieldEmptyExceptionHandler;
import com.example.todo_list_management.exception.NotFoundExceptionHandler;
import com.example.todo_list_management.exception.NotValidValueExceptionHandler;
import com.example.todo_list_management.model.entity.Category;
import com.example.todo_list_management.model.entity.Task;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.StatusRequest;
import com.example.todo_list_management.model.request.TaskRequest;
import com.example.todo_list_management.repository.CategoryRepository;
import com.example.todo_list_management.repository.TaskRepository;
import com.example.todo_list_management.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    private Integer getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp) authentication.getPrincipal();
        return userApp.getId();
    }

    // get all task
    @Override
    public List<Task> getAllTask() {
        if(taskRepository.findAllTasks().isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }
        return taskRepository.findAllTasks();
    }

    @Override
    public Task getTaskById(Integer taskId) {
        if(taskRepository.getTaskById(taskId) == null) {
            throw new NotFoundExceptionHandler("Task not found");
        }
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public Integer insertNewTask(TaskRequest taskRequest) {
        validateTaskRequest(taskRequest);
        return taskRepository.insertNewTask(taskRequest, getCurrentUser());
    }

    @Override
    public Integer updateTask(TaskRequest taskRequest, Integer taskId) {

        if(taskRepository.getTaskByCurrentUser(taskId, getCurrentUser()) == null) {
            throw new NotFoundExceptionHandler("Task not found");
        }

        validateTaskRequest(taskRequest);

        return taskRepository.updateTask(taskRequest, taskId, getCurrentUser());
    }

    private void validateTaskRequest(TaskRequest taskRequest) {
        if(!taskRequest.getStatus().equals("is_cancelled") &&
                !taskRequest.getStatus().equals("is_completed") &&
                !taskRequest.getStatus().equals("is_in_progress") &&
                !taskRequest.getStatus().equals("is_in_review")
        ) {
            throw new NotValidValueExceptionHandler("Status not in value of is_cancelled, is_completed, is_in_progress, is_in_review");
        }
        else if (taskRequest.getStatus().isEmpty()) {
            throw new FieldEmptyExceptionHandler("Field status is empty");
        }
        else if (taskRequest.getStatus().isBlank()) {
            throw new FieldEmptyExceptionHandler("Field status is blank");
        }
        else if(taskRequest.getTaskName().isBlank()) {
            throw new FieldEmptyExceptionHandler("Field taskName is blank");
        }
        else if(taskRequest.getTaskName().isEmpty()) {
            throw new FieldEmptyExceptionHandler("Field taskName is empty");
        }
        else if(taskRequest.getDescription().isBlank()) {
            throw new FieldEmptyExceptionHandler("Field description is blank");
        }
        else if(taskRequest.getDescription().isEmpty()) {
            throw new FieldEmptyExceptionHandler("Field description is empty");
        }

        Category category = categoryRepository.getCategoryById(taskRequest.getCategoryId());
        if(category == null) {
            throw new NotFoundExceptionHandler("Category id not found");
        }
    }

    @Override
    public Integer deleteTaskById(Integer taskId) {
        if(taskRepository.getTaskByCurrentUser(taskId, getCurrentUser()) == null) {
            throw new NotFoundExceptionHandler("Task not found");
        }
        return taskRepository.deleteTaskById(taskId, getCurrentUser());
    }

    @Override
    public List<Task> searchByStatus(String status) {
        if(taskRepository.searchByStatus(status, getCurrentUser()).isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }
        return taskRepository.searchByStatus(status, getCurrentUser());
    }

    @Override
    public Task getTaskByCurrentUser(Integer taskId) {
        if(taskRepository.getTaskByCurrentUser(taskId, getCurrentUser()) == null) {
            throw new NotFoundExceptionHandler("Task not found");
        }
        return taskRepository.getTaskByCurrentUser(taskId, getCurrentUser());
    }

    @Override
    public List<Task> getAllTaskByCurrentUser() {
        if(taskRepository.getAllTaskByCurrentUser(getCurrentUser()).isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }
        return taskRepository.getAllTaskByCurrentUser(getCurrentUser());
    }

    @Override
    public Integer changeTaskStatus(StatusRequest statusRequest, Integer taskId) {
        if(taskRepository.getTaskByCurrentUser(taskId, getCurrentUser()) == null) {
            throw new NotFoundExceptionHandler("Task not found");
        }
        return taskRepository.changeTaskStatus(statusRequest, taskId, getCurrentUser());
    }
}
