package sopeu.KickboardParkBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sopeu.KickboardParkBuddy.domain.Parking;

import javax.persistence.GeneratedValue;

@Data
@Builder
public class ParkingResponse {
    private Long id;
    private String placeName;
    private String address;
    private Double lat;
    private Double lng;

    //엔티티에 매핑
    public static ParkingResponse from(Parking parking) {
        return ParkingResponse.builder()
                .id(parking.getId())
                .placeName(parking.getPlaceName())
                .address(parking.getAddress())
                .lat(parking.getLat())
                .lng(parking.getLng())
                .build();
    }

}
