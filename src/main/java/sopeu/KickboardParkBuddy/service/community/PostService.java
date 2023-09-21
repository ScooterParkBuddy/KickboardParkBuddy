package sopeu.KickboardParkBuddy.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.domain.User;
import sopeu.KickboardParkBuddy.domain.Post;
import sopeu.KickboardParkBuddy.dto.post.PostCommentResponse;
import sopeu.KickboardParkBuddy.dto.post.PostRequest;
import sopeu.KickboardParkBuddy.dto.post.PostResponse;
import sopeu.KickboardParkBuddy.repository.UserRepository;
import sopeu.KickboardParkBuddy.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(PostRequest request) {
        Optional<User> findUser = userRepository.findById(request.getWriterId());
        Post post = new Post(request.getTitle(), request.getContents(), findUser, request.getBoardId());
        postRepository.save(post);
    }

    public List<PostResponse> getAllPosts(Long boardId) {
        List<Post> findPosts = postRepository.findByboardId(boardId);
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : findPosts) {
            responses.add(PostResponse.from(post));
        }
        return responses;
    }

    //게시물 하나 가져오기
    public PostCommentResponse getPost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postId=" + postId));
        return PostCommentResponse.from(findPost);
    }

    //게시물 수정
    @Transactional
    public void updatePost(PostRequest post, Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postId=" + postId));

        findPost.updateTitle(post.getTitle());  //생성자로 수정
        findPost.updateContents(post.getContents());
    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
