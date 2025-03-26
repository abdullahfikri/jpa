package dev.mfikri.jpa.entity;

import java.time.LocalDateTime;

public interface UpdatedAtAware {
    void setUpdatedAt(LocalDateTime updatedAt);
    void setCreatedAt(LocalDateTime updatedAt);
}
