package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopeu.KickboardParkBuddy.domain.Member;
import sopeu.KickboardParkBuddy.dto.member.KakaoTokenResponse;
import sopeu.KickboardParkBuddy.dto.member.KakaoUserInfoResponse;
import sopeu.KickboardParkBuddy.jwt.AuthTokens;
import sopeu.KickboardParkBuddy.service.member.KakaoApiService;
import sopeu.KickboardParkBuddy.service.member.MemberService;
import sopeu.KickboardParkBuddy.service.member.OAuthLoginService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final KakaoApiService kakaoApiService;
    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<AuthTokens> oauthLogin(@RequestParam("code") String code) throws Exception {
        KakaoTokenResponse token = kakaoApiService.getToken(code);  //토큰 받아오기
        KakaoUserInfoResponse userInfo = kakaoApiService.getUserInfo(token.getAccess_Token());  //토큰으로 정보 받아오기
//        Member member = memberService.getMember(userInfo.getEmail());  //기존가입자 체크
//        if(member == null) {
//            memberService.saveMember(userInfo);
//        }
//        Member result = memberService.getMember(userInfo.getEmail());
//        return result;
        //로그인 처리
        return ResponseEntity.ok(oAuthLoginService.login(userInfo));
    }

}
