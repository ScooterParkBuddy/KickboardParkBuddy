package sopeu.KickboardParkBuddy.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.domain.Member;
import sopeu.KickboardParkBuddy.dto.member.KakaoUserInfoResponse;
import sopeu.KickboardParkBuddy.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(KakaoUserInfoResponse info) throws Exception {  //회원가입
        Member member = new Member(info.getNickname(), info.getEmail());
        memberRepository.save(member);
    }

    public Member getMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null); // 값이 없을 때 null 반환하거나 다른 처리를 수행할 수 있음
    }




}
