package com.ll.rest.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private long id;

    @JsonProperty("createdDatetime")
    private LocalDateTime createTime;

    @JsonProperty("modifiedDatetime")
    private LocalDateTime modifyTime;

    private long authorId;

    private String authorName;

    private String title;

    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createTime = post.getCreateDate();
        this.modifyTime = post.getModifyDate();
        this.authorId = post.getAuthor().getId();
        this.authorName = post.getAuthor().getName();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
