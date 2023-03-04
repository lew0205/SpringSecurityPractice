package study.securityPrac.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.securityPrac.domain.member.Member;
import study.securityPrac.domain.member.dto.request.MemberSignUpReqDto;
import study.securityPrac.domain.member.dto.response.MemberSignUpResDto;
import study.securityPrac.domain.member.repository.MemberRepository;
import study.securityPrac.domain.member.service.MemberSignUpService;

@Service
@RequiredArgsConstructor
public class MemberSignUpServiceImpl implements MemberSignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberSignUpResDto execute(MemberSignUpReqDto memberSignupReqDto) {

        return new MemberSignUpResDto(memberRepository.save(memberSignupReqDto.toEntity(passwordEncoder.encode(memberSignupReqDto.getPassword()))));
    }
}
