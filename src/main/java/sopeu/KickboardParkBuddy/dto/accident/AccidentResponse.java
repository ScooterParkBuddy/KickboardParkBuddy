package sopeu.KickboardParkBuddy.dto.accident;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sopeu.KickboardParkBuddy.domain.Accident;
import sopeu.KickboardParkBuddy.dto.ParkingResponse;
@Data
@Slf4j
@Builder
public class AccidentResponse {
    private Long id;

    private String longitude;
    private String latitude;
    private String danger;

    //엔티티에 매핑
    public static AccidentResponse from(Accident accident) {
        return AccidentResponse.builder()
                .id(accident.getId())
                .longitude(String.valueOf(accident.getLongitude()))
                .latitude(String.valueOf(accident.getLatitude()))
                .danger(String.valueOf(accident.getDanger()))
                .build();
    }

}
