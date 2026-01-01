package com.project.todo.todos;

import com.project.todo.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "todo_categories")
public class TodoCategory {

    // look into this later --- 1
    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // look into these parameters --- 2
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoItem> tasks;

    protected TodoCategory() {}

    public TodoCategory(User user, String category) {
        this.user = user;
        this.category = category;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters and setters

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public List<TodoItem> getTasks() {
        return tasks;
    }

    // public helper functions

    public void addItem(TodoItem item) {
        tasks.add(item);
    }

    public void removeItem(TodoItem item) {
        tasks.remove(item);
    }

}

