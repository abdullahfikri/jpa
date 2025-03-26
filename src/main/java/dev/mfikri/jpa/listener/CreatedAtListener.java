package dev.mfikri.jpa.listener;

import dev.mfikri.jpa.entity.UpdatedAtAware;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class CreatedAtListener {

    @PrePersist
    public void setCreatedAt(UpdatedAtAware object) {
        object.setCreatedAt(LocalDateTime.now());
    }
}
