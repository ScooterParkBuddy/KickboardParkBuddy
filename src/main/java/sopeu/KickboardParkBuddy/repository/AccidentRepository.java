package sopeu.KickboardParkBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopeu.KickboardParkBuddy.domain.Accident;

public interface AccidentRepository extends JpaRepository<Accident, Long> {

}
