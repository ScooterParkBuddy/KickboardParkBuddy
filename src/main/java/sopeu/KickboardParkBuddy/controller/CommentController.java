package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sopeu.KickboardParkBuddy.dto.comment.CommentRequest;
import sopeu.KickboardParkBuddy.service.community.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment/new")
    public void createComment(@RequestBody CommentRequest request, @PathVariable Long postId) {
        commentService.save(request, postId);
    }

}