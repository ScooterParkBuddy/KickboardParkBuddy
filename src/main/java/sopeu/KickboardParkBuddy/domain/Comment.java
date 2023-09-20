package sopeu.KickboardParkBuddy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopeu.KickboardParkBuddy.domain.global.BaseEntity;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User replyMember;

    public Comment(String contents, Post post, Optional<User> member) {
        this.contents = contents;
        this.post = post;
        this.replyMember = member.orElse(null);
        post.getComment().add(this);
        member.orElse(null).getComment().add(this);
    }

    //수정 메서드
    public void updateContents(String contents) {
        this.contents = contents;
    }

    //생성자, 연관관계 메서드
    public Comment(String contents, Post post, User member) {
        this.contents = contents;
        this.post = post;
        this.replyMember = member;
        post.getComment().add(this);
        member.getComment().add(this);
    }
}
