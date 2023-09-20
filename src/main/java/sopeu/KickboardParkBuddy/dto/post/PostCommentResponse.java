package sopeu.KickboardParkBuddy.dto.post;

import lombok.Builder;
import lombok.Data;
import sopeu.KickboardParkBuddy.domain.Post;
import sopeu.KickboardParkBuddy.dto.comment.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostCommentResponse {
    private Long id;
    private String title;
    private String contents;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponse> comments;

    //엔티티에 매핑
    public static PostCommentResponse from(Post post) {
        List<CommentResponse> commentResponses = post.getComment().stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());

        return PostCommentResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .writerId(post.getWriter().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdateAt())
                .comments(commentResponses)
                .build();
    }
}
