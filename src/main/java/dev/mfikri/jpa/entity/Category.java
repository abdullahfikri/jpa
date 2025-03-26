package dev.mfikri.jpa.entity;

import dev.mfikri.jpa.listener.CreatedAtListener;
import dev.mfikri.jpa.listener.UpdatedAtListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@EntityListeners({
        UpdatedAtListener.class,
        CreatedAtListener.class
})
public class Category implements UpdatedAtAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String description;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
