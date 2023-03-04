package study.securityPrac.domain.member.dto.response;

import lombok.AllArgsConstructor;
import study.securityPrac.domain.member.Member;

public class MemberSignUpResDto {

    private Long id;
    private String email;
    private String password;
    private String name;

    public MemberSignUpResDto(Member member) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
    }
}
