//package sopeu.KickboardParkBuddy.service.member;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import sopeu.KickboardParkBuddy.dto.member.KakaoTokenResponse;
//import sopeu.KickboardParkBuddy.dto.member.KakaoUserInfoResponse;
//
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//
//@Service
//@Slf4j
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class KakaoApiService {
//    private final String tokenUri = "https://kauth.kakao.com/oauth/token";
//    private final String userInfoUri = "https://kapi.kakao.com/v2/user/me";
//    private final String kakaoLocalKey = URLDecoder.decode("f45f27c7b49797090f7f8c6cc60d0a2e", StandardCharsets.UTF_8);
//    private final String redirectUri = "http://localhost:8080/auth/kakao/callback";
//    public KakaoTokenResponse getToken(String code) {  //post
//
//        RestTemplate restTemplate = new RestTemplate();  //api 요청을 보내기 위해 spring에서 제공하는 restTemplate를 사용
//
//        //헤더 생성
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //httpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", kakaoLocalKey);
//        params.add("redirect_uri", redirectUri);
//        params.add("code", code);
//
//        //httpHeader와 httpbody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);
//
//        //http 요청하기 - post로
//        ResponseEntity<String> response = restTemplate.exchange(
//                tokenUri,
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // API Response로부터 body 뽑아내기
//        String body = response.getBody();
//        JSONObject json = new JSONObject(body);
//        // body에서 좌표 뽑아내기
//        String accessToken = json.getString("access_token");
//        String tokenType = json.getString("token_type");
//        String refreshToken = json.getString("refresh_token");
//        Integer expiresIn = json.getInt("expires_in");
//        String scope = json.getString("scope");
//        Integer refreshToeknExpiresInf = json.getInt("refresh_token_expires_in");
//
//        return new KakaoTokenResponse(accessToken, tokenType, refreshToken, expiresIn, scope, refreshToeknExpiresInf);
//    }
//
//    public KakaoUserInfoResponse getUserInfo(String token){  //post
//        RestTemplate restTemplate = new RestTemplate();  //api 요청을 보내기 위해 spring에서 제공하는 restTemplate를 사용
//        String apiKey = "Bearer " + token;
//        //헤더 생성
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        httpHeaders.add("Authorization", apiKey);
//
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(httpHeaders);
//
//        //http 요청하기 - post로
//        ResponseEntity<String> response = restTemplate.exchange(
//                userInfoUri,
//                HttpMethod.POST,
//                kakaoProfileRequest,
//                String.class
//        );
//        log.info(response.getBody());
//        // API Response로부터 body 뽑아내기
//        String body = response.getBody();
//        JSONObject json = new JSONObject(body);
//        Long userId = json.getLong("id");
//        String nickname = json.getJSONObject("kakao_account").getJSONObject("profile").getString("nickname");
//        String email = json.getJSONObject("kakao_account").getString("email");
//
//        return new KakaoUserInfoResponse(userId, nickname, email);
//
//    }
//}
