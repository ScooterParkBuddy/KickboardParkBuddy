package sopeu.KickboardParkBuddy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import sopeu.KickboardParkBuddy.domain.Comment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String contents;
    private Long replyMemberId;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getContents(), comment.getReplyMember().getId(),comment.getCreatedAt());
    }

}
