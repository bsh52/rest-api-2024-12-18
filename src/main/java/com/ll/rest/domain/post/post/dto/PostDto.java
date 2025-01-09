package com.ll.rest.domain.post.post.dto;

import com.ll.rest.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createTime = post.getCreateDate();
        this.modifyTime = post.getModifyDate();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
