package sopeu.KickboardParkBuddy.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import sopeu.KickboardParkBuddy.dto.ParkingResponse;

@Data
@AllArgsConstructor
public class KeywordSearchResponse {
    private String placeName;
    private String address;
    private String lng;
    private String lat;

}
