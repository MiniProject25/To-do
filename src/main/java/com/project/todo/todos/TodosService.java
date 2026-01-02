package com.project.todo.todos;

import com.project.todo.todos.dto.TodoCatRequest;
import com.project.todo.todos.dto.TodoCatResponse;
import com.project.todo.user.User;
import com.project.todo.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodosService {
    private final TodoCatRepository todosRepository;
//    private final TodoItemRepository todoItemRepository;
    private final UserService userService;
    private final TodoCatRepository todoCatRepository;

    public TodosService(TodoCatRepository todosRepository, UserService userService, TodoCatRepository todoCatRepository) {
        this.todosRepository = todosRepository;
//        this.todoItemRepository = todoItemRepository;
        this.userService = userService;
        this.todoCatRepository = todoCatRepository;
    }

    // create category
    public TodoCatResponse createTodoCat(TodoCatRequest todoCatRequest, UUID id) {
        User user = userService.getUser(id);

        TodoCategory todoCategory = new TodoCategory(user, todoCatRequest.category());
        todoCatRepository.save(todoCategory);

        return new TodoCatResponse(todoCategory, user);
    }

    // get categories
    public List<TodoCatResponse> getTodoCat(UUID userId) {
        List<TodoCategory> categories = todosRepository.findByUser_UserId(userId);
        User user = userService.getUser(userId);
        return categories.stream().map(todoCategory -> new TodoCatResponse(todoCategory, user)).toList();
    }

    // update category
    public TodoCatResponse updateCat(long id, TodoCatRequest todoCatRequest, UUID userId) {
        TodoCategory existing = todoCatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category does not exist"));

        existing.setCategory(todoCatRequest.category());
        todoCatRepository.save(existing);

        return new TodoCatResponse(existing.getCategory(), userId.toString());
    }

    // delete category
    public TodoCatResponse deleteCat(long id, UUID userId) {
        TodoCategory existing = todoCatRepository.findByIdAndUser_UserId(id, userId);
        todoCatRepository.delete(existing);
        return new TodoCatResponse(existing.getCategory(), userId.toString());
    }


}
