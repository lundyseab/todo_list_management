package com.example.todo_list_management.repository;

import com.example.todo_list_management.model.entity.Category;
import com.example.todo_list_management.model.request.CategoryRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryRepository {

    @Select("SELECT * FROM category_tb order by id asc")
    @Results(
            id = "categoryMapper",
            value = {
                    @Result(property = "categoryId", column = "id"),
                    @Result(property = "categoryName", column = "name"),
                    @Result(property = "userId", column = "user_id")
            }
    )
    List<Category> findAllCategory();

    @Select("SELECT * FROM category_tb WHERE id= #{categoryId}")
    @ResultMap("categoryMapper")
    Category getCategoryById(Integer categoryId);


    @Select("INSERT INTO category_tb(name, user_id) VALUES(#{req.categoryName},#{currentUserID}) RETURNING id")
    Integer saveCategory(@Param("req") CategoryRequest categoryRequest, Integer currentUserID);

    @Delete("DELETE FROM category_tb WHERE id = #{categoryId} and user_id = #{currentUserId}")
    boolean deleteCategoryById(Integer categoryId, Integer currentUserId);

    @Select("""
            update category_tb set name = #{req.categoryName} where id = #{categoryId} and user_id = #{currentUserId} returning id
            """)
    Integer updateCategory(@Param("req") CategoryRequest categoryRequest, Integer categoryId, Integer currentUserId);

    @Select("SELECT * FROM category_tb " +
            "where user_id = #{currentUserId} " +
            "order by id asc " +
            "LIMIT #{size} " +
            "OFFSET #{size} * (#{page} -1) ")
     @ResultMap("categoryMapper")
    List<Category> findAllCategoryOfCurrentUser(Integer page, Integer size, Integer currentUserId);

    @Select("SELECT * FROM category_tb " +
            "where user_id = #{currentUserId} " +
            "order by id desc " +
            "LIMIT #{size} " +
            "OFFSET #{size} * (#{page} -1) ")
    @ResultMap("categoryMapper")
    List<Category> findAllCategoryOfCurrentUserByDesc(Integer page, Integer size, Integer currentUserId);

    @Select(" SELECT * FROM category_tb WHERE id= #{categoryId} and user_id = #{currentUserId}")
    @ResultMap("categoryMapper")
    Category getCategoryOfUserById(Integer categoryId, Integer currentUserId);

    @Select("SELECT * FROM category_tb " +
            "where user_id = #{currentUserId} " +
            "order by id desc ")
    @ResultMap("categoryMapper")
    List<Category> findAllCategoryOfCurrentUserForFilter(Integer currentUserId);
}
