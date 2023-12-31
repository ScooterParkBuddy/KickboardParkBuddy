package sopeu.KickboardParkBuddy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sopeu.KickboardParkBuddy.domain.global.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    private Long boardId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    public Post(String title, String contents, User user, Long boardId) {
        this.title = title;
        this.contents = contents;
        this.writer = user;
        this.boardId = boardId;
    }

    public Post(String title, String contents, Optional<User> findUser, Long boardId) {
        this.title = title;
        this.contents = contents;
        this.writer = findUser.orElse(null);
        this.boardId = boardId;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}



