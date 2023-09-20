package sopeu.KickboardParkBuddy.service.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.domain.Comment;
import sopeu.KickboardParkBuddy.domain.User;
import sopeu.KickboardParkBuddy.domain.Post;
import sopeu.KickboardParkBuddy.dto.comment.CommentRequest;
import sopeu.KickboardParkBuddy.repository.CommentRepository;
import sopeu.KickboardParkBuddy.repository.UserRepository;
import sopeu.KickboardParkBuddy.repository.PostRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository memberRepository;

    @Transactional
    public void save(CommentRequest request, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postId=" + postId));
        Optional<User> findUser = memberRepository.findById(request.getReplyWriterId());
        Comment comment = new Comment(request.getContents(), post, findUser);
        commentRepository.save(comment);
    }

}

