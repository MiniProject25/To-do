package com.project.todo.todos;

import com.project.todo.todos.dto.*;
import com.project.todo.user.User;
import com.project.todo.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodosService {
    private final TodoCatRepository todosRepository;
    private final TodoItemRepository todoItemRepository;
    private final UserService userService;
    private final TodoCatRepository todoCatRepository;

    /* ---------------------------------- TODO CATEGORIES START ---------------------------------- */

    public TodosService(TodoCatRepository todosRepository, UserService userService, TodoCatRepository todoCatRepository, TodoItemRepository todoItemRepository) {
        this.todosRepository = todosRepository;
        this.todoItemRepository = todoItemRepository;
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

    /* ---------------------------------- TODO CATEGORIES END ---------------------------------- */

    /* ---------------------------------- TODO ITEMS START ---------------------------------- */

    // add todo item
    public TodoItemResponse addTodoItem(@Valid TodoItemRequest todoItemRequest, Long id, UUID userId) {
        TodoCategory todoCategory = todoCatRepository.findByIdAndUser_UserId(id, userId);
        if (todoCategory == null) {
            throw new EntityNotFoundException("Category does not exist");
        }
        TodoItem todoItem = new TodoItem(todoItemRequest.title(), todoItemRequest.dueDate(), todoItemRequest.steps(), todoCategory);
        todoItemRepository.save(todoItem);
        todoCategory.addItem(todoItem);
        return new TodoItemResponse(todoItem, todoCategory);
    }

    // delete todo item
    @Transactional
    public TodoItemResponse deleteTodoItem(Long id, long catId, UUID userId) {
        TodoCategory todoCategory = todoCatRepository.findByIdAndUser_UserId(catId, userId);

        if (todoCategory == null) {
            throw new EntityNotFoundException("Category does not exist");
        }

        TodoItem item = todoCategory.getTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item does not exist"));

        todoCategory.removeItem(item);

        return new TodoItemResponse(item);
    }

    // update todo item
    public TodoItemResponse updateTodoItem(long id, long catId, UUID userId, TodoItemUpdate todoItemUpdate) {
        TodoCategory todoCategory = todoCatRepository.findByIdAndUser_UserId(catId, userId);

        if (todoCategory == null) {
            throw new EntityNotFoundException("Category does not exist");
        }

        TodoItem todoItem = todoItemRepository.findByIdAndCategory(id, todoCategory);

        if (todoItem == null) {
            throw new EntityNotFoundException("Item does not exist");
        }

        if (todoItemUpdate.categoryId() != null) {
            // update the category of the item
            TodoCategory category = todoCatRepository.findByIdAndUser_UserId(todoItemUpdate.categoryId(), userId);
            if (category == null) {
                throw new EntityNotFoundException("Category does not exist");
            }
            todoItem.setCategory(category);
        }

        todoItem.updateFrom(todoItemUpdate);

        todoItemRepository.save(todoItem);

        return new TodoItemResponse(todoItem);
    }

    // view all todo items
    public List<TodoItemResponse> getTodoItems(UUID userId, Long id) {
        TodoCategory todoCategory = todoCatRepository.findByIdAndUser_UserId(id, userId);

        if (todoCategory == null) {
            throw new RuntimeException("Category does not exist");
        }

        return todoCategory.getTasks().stream().map(TodoItemResponse::new).toList();
    }



}
