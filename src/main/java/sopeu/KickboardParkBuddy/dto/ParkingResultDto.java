package sopeu.KickboardParkBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class ParkingResultDto {
    private Long parkingId;
    private String address;
    private String location;
    private String lng;
    private String lat;
    private String placeName;
    private Double distance;
}
