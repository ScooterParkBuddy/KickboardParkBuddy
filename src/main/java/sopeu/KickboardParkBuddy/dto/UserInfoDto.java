package sopeu.KickboardParkBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserInfoDto {
    private Long userId;
    private String email;
    private String name;
    private String refreshToken;
}
