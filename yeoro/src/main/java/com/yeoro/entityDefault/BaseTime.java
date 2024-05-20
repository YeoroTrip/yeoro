package com.yeoro.entityDefault;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class BaseTime {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public void onPrePersist(){
        if (this.createdAt == null) {
            this.createdAt = DateFormat.getCurrentTime();
        }
        this.updatedAt = this.createdAt;
    }

    public void onPreUpdate(){
        this.updatedAt = DateFormat.getCurrentTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseTime baseTime)) return false;
        return Objects.equals(getCreatedAt(), baseTime.getCreatedAt()) && Objects.equals(getUpdatedAt(), baseTime.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "BaseTime{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
