package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping
    public List<Post> getItems() {
        return postService.findAllByOrderByIdDesc();
    }

    @GetMapping("/{id}")
    public Post getItems(@PathVariable Long id) {
        return postService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public RsData deleteItems(@PathVariable Long id) {
        Post post = postService.findById(id).get();

        postService.delete(post);

        return new RsData("200-1", "%d번 글을 삭제하였습니다.".formatted(id));
    }


    record PostModifyReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData modifyItems(
            @PathVariable Long id,
            @RequestBody @Valid PostModifyReqBody reqBody
    ) {
        Post post = postService.findById(id).get();

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData("200-1", "%d번 글이 수정되었습니다.".formatted(id));
    }


    record PostWriteReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    @PostMapping
    public RsData writeItems(
            @RequestBody @Valid PostModifyReqBody reqBody
    ) {
        Post post = postService.write(reqBody.title, reqBody.content);

        return new RsData("200-1", "%d번 글이 작성되었습니다.".formatted(post.getId()));
    }
}