package sopeu.KickboardParkBuddy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sopeu.KickboardParkBuddy.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static sopeu.KickboardParkBuddy.security.JwtConstants.*;
import static sopeu.KickboardParkBuddy.security.JwtConstants.RT_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author : Hunseong-Park
 * @date : 2022-07-04
 */
@RequiredArgsConstructor
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        // 토큰 생성
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXP_TIME))
                .withClaim("nickName", request.getAttribute("nickname").toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(JWT_SECRET));

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("nickName", request.getAttribute("nickname").toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(JWT_SECRET));

        // Refresh Token DB에 저장
        userService.updateRefreshToken(user.getUsername(), refreshToken);

        // HTTP-only 쿠키 설정
//        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
//        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일
//        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정
//        refreshTokenCookie.setSameSite("None"); // Cross-Site 요청에서도 전송되도록 설정

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(7 * 24 * 60 * 60)
                .build();

        // 쿠키를 응답에 추가
        response.addHeader("Set-Cookie", cookie.toString());

        // Access Token을 Response Header로 전달
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setHeader(AT_HEADER, accessToken);

        // 토큰 정보를 담아서 응답으로 보내기
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(AT_HEADER, accessToken);

        // ObjectMapper를 사용하여 JSON 형태로 응답 전송
        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }

}
