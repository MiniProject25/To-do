package com.project.todo.todos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TodoCatRequest(@NotBlank @NotNull @Valid String category) {
}
