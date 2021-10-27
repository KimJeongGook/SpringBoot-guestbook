package orz.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {
    @CreatedDate 
    @Column(name = "regdate", updatable = false) //작성한 날짜, 시간
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate") //수정, 업데이트한 날짜, 시간
    private LocalDateTime modDate;
}
