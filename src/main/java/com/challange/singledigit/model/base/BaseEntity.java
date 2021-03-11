package com.challange.singledigit.model.base;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public @Data
class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)

    @Type(type="uuid-char")
    private UUID uid;

    @Column(name = "created_at", insertable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "last_updated_at", insertable = false)
    @UpdateTimestamp
    protected LocalDateTime lastUpdatedAt;

    @Column(name = "removed_at", insertable = false)
    protected LocalDateTime removedAt;

    @PrePersist
    public void handleUid() {
        this.setUid(UUID.randomUUID());
    }
}
