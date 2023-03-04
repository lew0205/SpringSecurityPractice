package study.securityPrac.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.securityPrac.domain.member.Member;
import study.securityPrac.domain.member.Role;

import java.util.Collections;

@Getter
@AllArgsConstructor
public class MemberSignUpReqDto {

    private String email;
    private String password;
    private String name;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
            .email(this.email)
            .password(encodedPassword)
            .name(this.name)
            .roles(Collections.singletonList(Role.ROLE_MEMBER))
            .build();
    }
}
