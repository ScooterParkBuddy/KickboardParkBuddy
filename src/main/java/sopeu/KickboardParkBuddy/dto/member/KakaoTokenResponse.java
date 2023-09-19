package sopeu.KickboardParkBuddy.dto.member;

import lombok.*;


@Data
@AllArgsConstructor
public class KakaoTokenResponse {
    private String access_Token;
    private String token_type;
    private String refresh_token;
    private int expries_in;
    private String scope;
    private int refresh_token_expires_in;
}
