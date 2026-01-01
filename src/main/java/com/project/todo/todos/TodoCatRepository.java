package com.project.todo.todos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCatRepository extends JpaRepository<TodoCategory,Long> {
}
