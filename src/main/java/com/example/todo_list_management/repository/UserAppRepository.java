package com.example.todo_list_management.repository;

import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.UserAppRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserAppRepository {

    @Select("""
            select * from user_tb where email = #{userEmail}
            """)
    UserApp getUserByEmail(String userEmail);

    @Select("""
            insert into user_tb(email, password)
            values(#{req.email}, #{req.password})
            returning *;
            """)
    UserApp insertUser(@Param("req") UserAppRequest userAppRequest);


    @Select("""
            select email from user_tb where email = #{userEmail}
            """)
    String getEmail(String email);
}
