package study.securityPrac.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.securityPrac.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberSignInReqDto {

    private String email;
    private String password;
}
