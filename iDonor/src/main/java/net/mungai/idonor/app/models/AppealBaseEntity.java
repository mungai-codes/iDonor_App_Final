package net.mungai.idonor.app.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AppealBaseEntity {

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AppealBaseEntity() {
    }

    public AppealBaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "AppealBaseEntity{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
