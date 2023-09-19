package sopeu.KickboardParkBuddy.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoUserInfoResponse {
    private Long id;
    private String nickname;
    private String email;
}
