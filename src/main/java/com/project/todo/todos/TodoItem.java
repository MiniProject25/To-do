package com.project.todo.todos;

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
    private long id;

    private String title;

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

    public TodoItem(String title, LocalDateTime dueDate, Boolean completed, List<String> steps, TodoCategory category) {
        this.title = title;
        this.dueDate = dueDate;
        this.completed = completed;
        this.steps = steps != null ? steps : new ArrayList<>();
        this.category = category;
    }

    public long getId() {
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
}
