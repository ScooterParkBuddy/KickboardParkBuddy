package sopeu.KickboardParkBuddy.dto.post;
import lombok.Builder;
import lombok.Data;
import sopeu.KickboardParkBuddy.domain.Post;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String contents;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //엔티티에 매핑
    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .writerId(post.getWriter().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdateAt())
                .build();
    }
}
