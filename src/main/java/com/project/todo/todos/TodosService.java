package com.project.todo.todos;

import com.project.todo.todos.dto.TodoCatRequest;
import com.project.todo.todos.dto.TodoCatResponse;
import com.project.todo.user.User;
import com.project.todo.user.UserService;
import org.springframework.stereotype.Service;

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


}
