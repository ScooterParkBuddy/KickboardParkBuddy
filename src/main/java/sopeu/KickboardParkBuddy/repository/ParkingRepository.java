package sopeu.KickboardParkBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopeu.KickboardParkBuddy.domain.Parking;


import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    @Query(value = "SELECT parking_id, address, location, longitude, latitude, place_name, ST_Distance_Sphere(POINT(:x, :y), location) AS distance from parking WHERE ST_Distance_Sphere(POINT(:x, :y), POINT(longitude, latitude)) <= 2000 " +
            "ORDER BY distance;", nativeQuery = true)
    List<Object[]> searchParking(@Param("x")Double x, @Param("y")Double y);

}
