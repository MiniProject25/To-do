package com.project.todo.todos.dto;

import com.project.todo.todos.TodoCategory;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record TodoItemRequest(@NotBlank String title, LocalDateTime dueDate, List<String> steps, Boolean completed) {

}
