package study.securityPrac.domain.member.service;

import org.springframework.stereotype.Service;
import study.securityPrac.domain.member.dto.request.MemberSignUpReqDto;
import study.securityPrac.domain.member.dto.response.MemberSignUpResDto;

@Service
public interface MemberSignUpService {

    public MemberSignUpResDto execute(MemberSignUpReqDto memberSignupReqDto);
}
