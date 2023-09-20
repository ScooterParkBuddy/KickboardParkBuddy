package sopeu.KickboardParkBuddy.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Long replyWriterId;
    private String contents;
}
