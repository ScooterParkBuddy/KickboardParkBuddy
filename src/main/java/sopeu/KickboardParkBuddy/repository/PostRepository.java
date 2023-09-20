package sopeu.KickboardParkBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopeu.KickboardParkBuddy.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
