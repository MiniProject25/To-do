package com.project.todo.todos.dto;

import com.project.todo.todos.TodoCategory;
import com.project.todo.todos.TodoItem;

import java.time.LocalDateTime;
import java.util.List;

public record TodoItemResponse(long id, String title, Boolean completed, List<String> steps, LocalDateTime dueDate, String category) {
    public TodoItemResponse(TodoItem todoItem, TodoCategory todoCategory) {
        this (
                todoItem.getId(),
                todoItem.getTitle(),
                todoItem.getCompleted(),
                todoItem.steps(),
                todoItem.getDueDate(),
                todoCategory.getCategory()
        );
    }

    public TodoItemResponse(TodoItem todoItem) {
        this(
                todoItem.getId(),
                todoItem.getTitle(),
                null,
                null,
                todoItem.getDueDate(),
                null
        );
    }
}