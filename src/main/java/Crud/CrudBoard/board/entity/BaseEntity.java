package Crud.CrudBoard.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 시간정보를 다루는 클래스
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false) // update 할때는 관여를 안함
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false) // insert 할때는 관여를 안함
    private LocalDateTime updatedTime;
}
