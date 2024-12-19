package com.ll.rest.domain.post.post.dto;

import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.global.jpa.entity.BaseTime;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto extends BaseTime {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
