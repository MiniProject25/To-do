package com.project.todo.todos;

import com.project.todo.todos.dto.*;
import com.project.todo.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/item")
    public TodoItemResponse addTodoItem(@RequestBody @Valid TodoItemRequest todoItemRequest, @RequestParam Long id) {
        UserResponse userResponse = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosService.addTodoItem(todoItemRequest, id, userResponse.getUserID());
    }

    @PutMapping("/item")
    public TodoItemResponse updateTodoItem(@RequestBody @Valid TodoItemUpdate todoItemUpdate, @RequestParam long id, @RequestParam long catId) {
        UserResponse userResponse = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosService.updateTodoItem(id, catId, userResponse.getUserID(), todoItemUpdate);
    }

    @DeleteMapping("/item")
    public TodoItemResponse deleteTodoItem(@RequestParam Long id, @RequestParam long catId) {
        UserResponse userResponse = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosService.deleteTodoItem(id, catId, userResponse.getUserID());
    }

    @GetMapping("/item")
    public List<TodoItemResponse> getTodoItems(@RequestParam long id) {
        UserResponse userResponse = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosService.getTodoItems(userResponse.getUserID(), id);
    }

    @PutMapping("/item/complete")
    public TodoItemResponse completeTodoItem(@RequestParam Long id, @RequestParam long catId, @RequestParam Boolean status) {
        UserResponse userResponse = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosService.setComplete(id, catId, userResponse.getUserID(), status);
    }

}
