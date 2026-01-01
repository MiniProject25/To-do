package com.project.todo.todos.dto;

import com.project.todo.todos.TodoCategory;
import com.project.todo.user.User;

public record TodoCatResponse(String category, String userId) {

    public TodoCatResponse(TodoCategory todoCategory, User user) {
        this(
            todoCategory.getCategory(),
                user.getUserId().toString()
        );
    }
}
