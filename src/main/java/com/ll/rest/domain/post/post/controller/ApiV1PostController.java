package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.member.member.entity.Member;
import com.ll.rest.domain.member.member.service.MemberService;
import com.ll.rest.domain.post.post.dto.PostDto;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;
    private final MemberService memberService;


    @GetMapping
    public List<PostDto> getItems() {
        return postService
                .findAllByOrderByIdDesc()
                .stream()
                .map(PostDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getItem(@PathVariable long id) {
        return postService.findById(id)
                .map(PostDto::new)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public RsData<Void> deleteItem(@PathVariable long id) {
        Post post = postService.findById(id).get();
        postService.delete(post);

        return new RsData<>("200-1", "%d번 글 삭제 완료".formatted(id));
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
    public RsData<PostDto> modifyItem(@PathVariable long id, @RequestBody @Valid PostModifyReqBody reqBody) {
        Post post = postService.findById(id).get();

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>("200-1", "%d번 글 수정 완료".formatted(id), new PostDto(post));
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
    record PostWriteResBody(
            PostDto item,
            long totalCount
    ) {

    }
    // 응답코드를 수정하려면 ResponseEntity로 리턴하면 된다

    @PostMapping
    public RsData<PostWriteResBody> writeItem(@RequestBody @Valid PostWriteReqBody reqBody) {
        Member actor = memberService.findByUsername("user3").get();

        Post post = postService.write(actor, reqBody.title, reqBody.content);

        return new RsData<>(
                "201-1",
                "%d번 글 작성 완료".formatted(post.getId()),
                new PostWriteResBody(new PostDto(post), postService.count())
        );
    }
}
