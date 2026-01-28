package com.project.todo.todos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.todo.todos.dto.TodoItemRequest;
import com.project.todo.todos.dto.TodoItemUpdate;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todo_items")
public class TodoItem {

    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dueDate;

    private Boolean completed;

    @ElementCollection
    @CollectionTable(
            name = "todo_steps",
            joinColumns = @JoinColumn(name = "todo_item_id")
    )
    @Column(name = "step")
    private List<String> steps;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TodoCategory category;

    protected TodoItem() {}

    public TodoItem(String title, LocalDateTime dueDate, List<String> steps, TodoCategory category) {
        this.title = title;
        this.dueDate = dueDate;
        this.completed = false;
        this.steps = steps != null ? steps : new ArrayList<>();
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public List<String> steps() {
        return steps;
    }
    public TodoCategory getCategory() {
        return category;
    }


    public void setCategory(TodoCategory category) {
        this.category = category;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void updateFrom(TodoItemUpdate todoItemUpdate) {
        if (todoItemUpdate.title() != null && !todoItemUpdate.title().equals(this.title)) {
            this.title = todoItemUpdate.title();
        }
        if (todoItemUpdate.dueDate() != null && !todoItemUpdate.dueDate().equals(this.dueDate)) {
            this.dueDate = todoItemUpdate.dueDate();
        }
        if (todoItemUpdate.steps() != null && !todoItemUpdate.steps().equals(this.steps)) {
            this.steps = todoItemUpdate.steps();
        }
        if (todoItemUpdate.completed() != null && !todoItemUpdate.completed().equals(this.completed)) {
            this.completed = todoItemUpdate.completed();
        }
    }
}
