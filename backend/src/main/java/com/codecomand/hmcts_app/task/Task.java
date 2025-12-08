package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TASKS")
@Entity
public class Task extends BaseEntity {

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private TaskStatus status;

    @Column(name = "DUE_DATE", nullable = false)
    private LocalDateTime dueDateTime;


    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public boolean isOverdue(){
        return dueDateTime != null && LocalDateTime.now().isAfter(dueDateTime) && status != TaskStatus.COMPLETED;
    }

    public boolean isDueSoon(){
        if (dueDateTime == null || status == TaskStatus.COMPLETED){
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        return dueDateTime.isAfter(now) && dueDateTime.isBefore(tomorrow);
    }

}
