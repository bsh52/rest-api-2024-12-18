package com.ll.rest.domain.member.member.controller;

import com.ll.rest.domain.member.member.dto.MemberDto;
import com.ll.rest.domain.member.member.entity.Member;
import com.ll.rest.domain.member.member.service.MemberService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    record MemberJoinReqBody(
            @NotBlank
            @Length(min = 4)
            String username,
            @NotBlank
            @Length(min = 4)
            String password,
            @NotBlank
            @Length(min = 2)
            String nickname
    ) {
    }

    // 응답코드를 수정하려면 ResponseEntity로 리턴하면 된다
    @PostMapping("/join")
    public RsData<MemberDto> writeItem(@RequestBody @Valid MemberJoinReqBody reqBody) {
        Member member = memberService.join(reqBody.username, reqBody.password, reqBody.nickname);

        return new RsData<>(
                "201-1",
                "%s님 환영합니다.".formatted(member.getNickname()),
                new MemberDto(member)
        );
    }
}
