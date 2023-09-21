package sopeu.KickboardParkBuddy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String contents;
    private Long writerId;
    private Long boardId;
}
