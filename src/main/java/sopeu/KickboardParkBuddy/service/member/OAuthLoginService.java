package sopeu.KickboardParkBuddy.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopeu.KickboardParkBuddy.domain.Member;
import sopeu.KickboardParkBuddy.dto.member.KakaoUserInfoResponse;
import sopeu.KickboardParkBuddy.jwt.AuthTokens;
import sopeu.KickboardParkBuddy.jwt.AuthTokensGenerator;
import sopeu.KickboardParkBuddy.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokens login(KakaoUserInfoResponse kakaoUserInfoResponse) {
        Long memberId = findOrCreateMember(kakaoUserInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(KakaoUserInfoResponse kakaoUserInfoResponse) {
        return memberRepository.findByEmail(kakaoUserInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(kakaoUserInfoResponse));
    }

    private Long newMember(KakaoUserInfoResponse kakaoUserInfoResponse) {
        Member member = Member.builder()
                .email(kakaoUserInfoResponse.getEmail())
                .username(kakaoUserInfoResponse.getNickname())
                .build();
        return memberRepository.save(member).getId();
    }
}