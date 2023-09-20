package sopeu.KickboardParkBuddy.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username; //이메일
    private String password;
    private String nickname;  //이름

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "replyMember", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();
    private String refreshToken;

    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }



}
