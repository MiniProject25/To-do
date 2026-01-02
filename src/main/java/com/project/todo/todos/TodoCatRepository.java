package com.project.todo.todos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TodoCatRepository extends JpaRepository<TodoCategory,Long> {
    List<TodoCategory> findByUser_UserId(UUID userId);
    TodoCategory findByIdAndUser_UserId(long id, UUID userId);
}
