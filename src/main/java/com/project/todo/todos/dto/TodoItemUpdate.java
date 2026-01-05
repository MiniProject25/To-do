package com.project.todo.todos.dto;

import com.project.todo.todos.TodoCategory;

import java.time.LocalDateTime;
import java.util.List;

public record TodoItemUpdate(Long categoryId, String title, Boolean completed, LocalDateTime dueDate, List<String> steps) {

}
