package sopeu.KickboardParkBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopeu.KickboardParkBuddy.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
