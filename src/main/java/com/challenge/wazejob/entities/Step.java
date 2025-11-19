package com.challenge.wazejob.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "step_id", nullable = false, updatable = false)
    private UUID stepId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "checkpoint_id", nullable = false)
    private Checkpoint checkpoint;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "content_url", length = 1000)
    private String contentUrl;

    @Column(name = "st_order", nullable = false)
    private Integer stepOrder;

    @Column(nullable = false)
    private Boolean completed = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
