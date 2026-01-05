package com.project.todo.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    TodoItem findByIdAndCategory(Long id, TodoCategory category);
}
