package com.zerobase.munbanggu.study.model.entity;


import com.zerobase.munbanggu.user.model.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD:src/main/java/com/zerobase/munbanggu/study/model/entity/StudyMember.java
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
=======
>>>>>>> a4cfaa526038dd21efedae8d989c803cf049a431:src/main/java/com/zerobase/munbanggu/study/model.entity/StudyMember.java
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDateTime certification_date;

    private List<Checklist> checklistList;
}
