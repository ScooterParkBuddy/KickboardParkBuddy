package sopeu.KickboardParkBuddy.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sopeu.KickboardParkBuddy.domain.User;
//import com.hunseong.jwt.domain.Role;
import sopeu.KickboardParkBuddy.dto.AliasRequestDto;
import sopeu.KickboardParkBuddy.dto.UserInfoDto;
import sopeu.KickboardParkBuddy.dto.UsertDto;
//import com.hunseong.jwt.domain.dto.RoleToUserRequestDto;
import sopeu.KickboardParkBuddy.repository.UserRepository;
//import com.hunseong.jwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static sopeu.KickboardParkBuddy.security.JwtConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Hunseong-Park
 * @date : 2022-07-04
 */
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //토큰에서 회원 정보 추출
    public UserInfoDto getUserInfo(String accesstoken) {

        String[] token = accesstoken.split(" ");


        String email = Jwts.parser().setSigningKey(JWT_SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token[1])
                .getBody()
                .getSubject();
        Optional<User> byUsername = userRepository.findByUsername(email);
        UserInfoDto userInfo = new UserInfoDto(byUsername.get().getId(), email, byUsername.get().getNickname(), byUsername.get().getRefreshToken());
        return userInfo;
    }


    //토큰에서 회원 이름 추출
    public String getUsernickName(String accesstoken) {

        String[] token = accesstoken.split(" ");


        return Jwts.parser().setSigningKey(JWT_SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(accesstoken)
                .getBody()
                .get("nickName",String.class);
    }

    // Request의 Header에서 token 값을 가져옵니다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //유저 점보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserDetailsService - loadUserByUsername : 사용자를 찾을 수 없습니다."));

//        List<SimpleGrantedAuthority> authorities = account.getRoles()
//                .stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

//        return new User(account.getUsername(), account.getPassword(), authorities);

        // authorities 대신 Collections.EMPTY_LIST을 넣어 Role 없이 인증가능하게 했다
        //CustomAuthProvider 의 authenticate 로 복귀
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),Collections.EMPTY_LIST);
    }

    @Override
    public void saveUser(UsertDto dto) {

        validateDuplicateUsername(dto);
        dto.encodePassword(passwordEncoder.encode(dto.getPassword()));


        userRepository.save(dto.toEntity()).getId();

//        Optional<Account> account = accountRepository.findByUsername(dto.getUsername());
//
//
//
//        addRoleToUser(account,new Role("ROLE_USER"));

    }

    private void validateDuplicateUsername(UsertDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("이미 존재하는 ID입니다.");
        }
    }


    // =============== TOKEN ============ //

    @Override
    public void updateRefreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.updateRefreshToken(refreshToken);
    }

    @Override
    public Map<String, String> refresh(String refreshToken) {

        // === Refresh Token 유효성 검사 === //
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);



        // === Access Token 재발급 === //
        long now = System.currentTimeMillis();
        String username = decodedJWT.getSubject();
        String nickName = decodedJWT.getClaim("nickName").toString();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
        }
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(now + AT_EXP_TIME))
                .withClaim("nickName", nickName)

//                .withClaim("roles", account.getRoles().stream().map(Role::getName)
//                        .collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(JWT_SECRET));
        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        if (diffMin < 5) {
            String newRefreshToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("nickName", nickName)
                    .withExpiresAt(new Date(now + RT_EXP_TIME))
                    .sign(Algorithm.HMAC256(JWT_SECRET));
            accessTokenResponseMap.put(RT_HEADER, newRefreshToken);
            user.updateRefreshToken(newRefreshToken);
        }

        accessTokenResponseMap.put(AT_HEADER, accessToken);
        return accessTokenResponseMap;
    }
    @Override
    public void deleteUser(String email){
        Optional<User> byUsername = userRepository.findByUsername(email);
        userRepository.deleteById(byUsername.get().getId());
    }

    @Override
    public void setAlias(AliasRequestDto aliasRequestDto) {
        Optional<User> findUserOptional = userRepository.findById(aliasRequestDto.getUserId());

        if (findUserOptional.isPresent()) {
            User user = findUserOptional.get();
            user.saveAlias(aliasRequestDto.getAlias());
            userRepository.save(user); // Assuming you need to save the user after modifying it
        } else {
            // Handle the case where the user with the given ID is not found
            // You can throw an exception, log an error, or perform any other desired action.
        }
    }

    @Override
    public String getAlias(Long userId){
        Optional<User> findUser = userRepository.findById(userId);
        return findUser.get().getAlias();
    }


}
