package com.example.todo_list_management.service;

import com.example.todo_list_management.model.entity.Task;
import com.example.todo_list_management.model.request.StatusRequest;
import com.example.todo_list_management.model.request.TaskRequest;

import java.util.List;

public interface TaskService {
    List<Task> getAllTask();

    Task getTaskById(Integer taskId);

    Integer insertNewTask(TaskRequest taskRequest);

    Integer updateTask (TaskRequest taskRequest, Integer taskId);

    Integer deleteTaskById (Integer taskId);

    List<Task> searchByStatus(String status);

    Task getTaskByCurrentUser(Integer integer);
    List<Task> getAllTaskByCurrentUser();

    Integer changeTaskStatus(StatusRequest statusRequest, Integer taskId);
}
