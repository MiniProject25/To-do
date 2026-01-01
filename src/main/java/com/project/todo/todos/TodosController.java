package com.project.todo.todos;

import com.project.todo.todos.dto.TodoCatRequest;
import com.project.todo.todos.dto.TodoCatResponse;
import com.project.todo.user.dto.UserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class TodosController {
    private final TodosService todosService;

    public TodosController(TodosService todosService) {
        this.todosService = todosService;
    }

    @PostMapping("/")
    public TodoCatResponse addTodoCategory(@RequestBody TodoCatRequest todoCatRequest) {
        UserResponse user = (UserResponse)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return todosService.createTodoCat(todoCatRequest, user.getUserID());
        }
        return null;
    }
}
