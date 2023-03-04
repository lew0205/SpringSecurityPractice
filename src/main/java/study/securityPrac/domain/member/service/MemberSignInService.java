package study.securityPrac.domain.member.service;

import org.springframework.stereotype.Service;
import study.securityPrac.domain.member.dto.response.MemberSignInResDto;

@Service
public interface MemberSignInService {

    public MemberSignInResDto execute();
}
