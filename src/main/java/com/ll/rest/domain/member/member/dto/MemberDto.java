package com.ll.rest.domain.member.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.member.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class MemberDto {
    private long id;

    @JsonProperty("createdDatetime")
    private LocalDateTime createTime;

    @JsonProperty("modifiedDatetime")
    private LocalDateTime modifyTime;

    private String username;

    private String password;

    private String nickname;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createTime = member.getCreateDate();
        this.modifyTime = member.getModifyDate();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}