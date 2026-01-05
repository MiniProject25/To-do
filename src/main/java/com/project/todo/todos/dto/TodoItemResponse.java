package com.project.todo.todos.dto;

import com.project.todo.todos.TodoCategory;
import com.project.todo.todos.TodoItem;

import java.time.LocalDateTime;
import java.util.List;

public record TodoItemResponse(String title, List<String> steps, LocalDateTime dueDate, String category) {
    public TodoItemResponse(TodoItem todoItem, TodoCategory todoCategory) {
        this (
                todoItem.getTitle(),
                todoItem.steps(),
                todoItem.getDueDate(),
                todoCategory.getCategory()
        );
    }

    public TodoItemResponse(TodoItem todoItem) {
        this(
                todoItem.getTitle(),
                null,
                todoItem.getDueDate(),
                null
        );
    }
}