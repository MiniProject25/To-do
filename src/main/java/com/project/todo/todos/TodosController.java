package com.project.todo.todos;

import com.project.todo.todos.dto.TodoCatRequest;
import com.project.todo.todos.dto.TodoCatResponse;
import com.project.todo.user.dto.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class TodosController {
    private final TodosService todosService;
    private final TodoCatRepository todoCatRepository;

    public TodosController(TodosService todosService, TodoCatRepository todoCatRepository) {
        this.todosService = todosService;
        this.todoCatRepository = todoCatRepository;
    }

    @PostMapping("/")
    public TodoCatResponse addTodoCategory(@RequestBody TodoCatRequest todoCatRequest) {
        UserResponse user = (UserResponse)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return todosService.createTodoCat(todoCatRequest, user.getUserID());
        }
        return null;
    }

    @GetMapping("/")
    public List<TodoCatResponse> getTodoCategory() {
        UserResponse user = (UserResponse)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return todosService.getTodoCat(user.getUserID());
        }
        return null;
    }

    @PutMapping("/{id}")
    public TodoCatResponse updateCategoryMetadata(@RequestBody TodoCatRequest todoCatRequest, @PathVariable long id) {
        UserResponse user = (UserResponse)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosService.updateCat(id, todoCatRequest, user.getUserID());
    }

    @DeleteMapping("/{id}")
    public TodoCatResponse deleteCategory(@PathVariable long id) {
        UserResponse user = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return todosService.deleteCat(id, user.getUserID());
        }
        return null;
    }

}
