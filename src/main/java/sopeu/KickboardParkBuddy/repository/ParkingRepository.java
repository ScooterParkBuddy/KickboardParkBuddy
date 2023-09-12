package sopeu.KickboardParkBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopeu.KickboardParkBuddy.domain.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

}
