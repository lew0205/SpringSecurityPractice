package study.securityPrac.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.securityPrac.domain.member.dto.request.MemberSignInReqDto;
import study.securityPrac.domain.member.dto.request.MemberSignUpReqDto;
import study.securityPrac.domain.member.dto.response.MemberSignUpResDto;
import study.securityPrac.domain.member.service.MemberSignInService;
import study.securityPrac.domain.member.service.MemberSignUpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberSignUpService memberSignUpService;
    private final MemberSignInService memberSignInService;

    @PostMapping("/signup")
    private ResponseEntity<T> joinMember(MemberSignUpReqDto memberSignUpReqDto) {
        return memberSignUpService.execute(memberSignUpReqDto);
    }

    @PostMapping
    private void loginMember(MemberSignInReqDto memberSignInReqDto){
        memberSignInService.execute();
    }
}
