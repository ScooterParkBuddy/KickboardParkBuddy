package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sopeu.KickboardParkBuddy.dto.post.PostCommentResponse;
import sopeu.KickboardParkBuddy.dto.post.PostRequest;
import sopeu.KickboardParkBuddy.dto.post.PostResponse;
import sopeu.KickboardParkBuddy.service.community.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    //전체 게시글 조회
    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getAllPosts();
    }

    //게시글 하나 조회(댓글 포함)
    @GetMapping("/{postId}")
    public PostCommentResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    //게시글 생성
    @PostMapping("/new")
    public void createPost(@RequestBody PostRequest request) {
        postService.save(request);
    }

    //게시글 수정
    @PutMapping("/{postId}")
    public void editPost(@RequestBody PostRequest post, @PathVariable Long postId) {
        postService.updatePost(post, postId);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
