package sopeu.KickboardParkBuddy.service;

import sopeu.KickboardParkBuddy.dto.UserInfoDto;
import sopeu.KickboardParkBuddy.dto.UsertDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : Hunseong-Park
 * @date : 2022-07-04
 */
public interface UserService {
    void saveUser(UsertDto dto);   //유저정보 저장

    void updateRefreshToken(String username, String refreshToken);  //RefreshToken 업데이트

    Map<String, String> refresh(String refreshToken);  //RefreshToken 으로 AccessToken 받아올때 사용

    String resolveToken(HttpServletRequest request);   // Request의 Header에서 token 값을 가져옵니다.
    UserInfoDto getUserEmail(String token);  //토큰에서 회원 아이디(= 이메일) 추출
}
