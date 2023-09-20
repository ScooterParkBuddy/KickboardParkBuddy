package sopeu.KickboardParkBuddy.domain.global;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass  //BaseEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식함
@EntityListeners(AuditingEntityListener.class)  //공통(Auditing) 기능 설정
public abstract class BaseEntity {
    @CreatedDate  //Entity가 최초 생성되어 저장될 때 시간이 자동으로 저장
    private LocalDateTime createdAt;

    @LastModifiedBy  //조회한 Entity의 값을 변경할 때 시간이 자동으로 저장
    private LocalDateTime updateAt;
}