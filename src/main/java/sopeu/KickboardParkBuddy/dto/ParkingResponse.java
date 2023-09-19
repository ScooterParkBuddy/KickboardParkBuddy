package sopeu.KickboardParkBuddy.dto;

import lombok.*;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import sopeu.KickboardParkBuddy.domain.Parking;

@Data
@Slf4j
@Builder
public class ParkingResponse {
    private Long id;
    private String placeName;
    private String address;
    private String longitude;
    private String latitude;

    //엔티티에 매핑
    public static ParkingResponse from(Parking parking) {
        return ParkingResponse.builder()
                .id(parking.getId())
                .placeName(parking.getPlaceName())
                .address(parking.getAddress())
                .longitude(String.valueOf(parking.getLocation().getX()))
                .latitude(String.valueOf(parking.getLocation().getY()))
                .build();
    }

}
