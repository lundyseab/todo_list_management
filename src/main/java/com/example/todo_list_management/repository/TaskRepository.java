package com.example.todo_list_management.repository;

import com.example.todo_list_management.model.entity.Task;
import com.example.todo_list_management.model.request.TaskRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskRepository {
    //Query all Task
    @Select("""
            SELECT * FROM task_tb;
            """)
    @Results(id = "taskMap",
            value = {
                    @Result(property = "taskId", column = "id"),
                    @Result(property = "taskName", column = "name"),
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "categoryId", column = "id")
            }
    )
    List<Task> findAllTasks();

    //Query task by id
    @Select("SELECT * FROM task_tb WHERE id = #{taskId} order by id asc")
    @ResultMap("taskMap")
    Task getTaskById(Integer taskId);

    @Select("SELECT * FROM task_tb WHERE id = #{taskId} and user_id = #{currentUserId} order by id asc")
    @ResultMap("taskMap")
    Task getTaskByCurrentUser(Integer taskId, Integer currentUserId);

    @Select("""
            INSERT INTO task_tb (name, description, date, status, user_id, category_id)
            VALUES (#{req.taskName}, #{req.description}, #{req.date}, #{req.status}, #{currentUserId}, #{req.categoryId})
            returning id;
            """)
    Integer insertNewTask(@Param("req") TaskRequest taskRequest, Integer currentUserId);

    @Select("""
            UPDATE task_tb 
            SET name = #{req.taskName}, description = #{req.description}, status = #{req.status}, category_id = #{req.categoryId}
            WHERE id = #{taskId}
            and user_id = #{currentUserId}
            returning id;
            """)
    Integer updateTask(@Param("req") TaskRequest taskRequest, Integer taskId, Integer currentUserId);

    @Select("""
            DELETE FROM task_tb WHERE id = #{taskId} and user_id = #{currentUserId} returning id;
            """)
    Integer deleteTaskById(Integer taskId, Integer currentUserId);

    @Select("SELECT * FROM task_tb WHERE status = #{status} and user_id = #{currentUserId} order by id asc")
    @ResultMap("taskMap")
    List<Task> searchByStatus(String status, Integer currentUserId);

    @Select("""
                select * from task_tb where user_id = #{currentUserId} order by id asc;
            """)
    @ResultMap("taskMap")
    List<Task> getAllTaskByCurrentUser(Integer currentUserId);
}
