package sopeu.KickboardParkBuddy.service.parking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.dto.Coordinates;
import sopeu.KickboardParkBuddy.domain.Parking;
import sopeu.KickboardParkBuddy.dto.ParkingResponse;
import sopeu.KickboardParkBuddy.repository.ParkingRepository;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingApiService parkingApiService;
    private final ParkingRepository parkingRepository;
    private static final int WGS84_STANDARD_SRID = 4_326;

    @PostConstruct
    @Transactional
    public void saveParkingInfo() throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/Users/shinhyejin/Downloads/KickboardParkBuddy/src/main/resources/gangnam.json");
        JSONArray dateArray = (JSONArray) parser.parse(reader);

        for (Object obj : dateArray) {
            JSONObject element = (JSONObject) obj;
            String placeName = (String) element.get("name");
            String address = (String) element.get("address");

            Coordinates coordinate = parkingApiService.getCoordinate(address);
            Double lng = Double.parseDouble(coordinate.getX());
            Double lat = Double.parseDouble(coordinate.getY());

            Point point = getPoint(lng, lat);
            Parking parkingInfo = new Parking(placeName, address, point, lng, lat);
            parkingRepository.save(parkingInfo);
        }
    }

    public List<ParkingResponse> getAllParkings() {
        List<Parking> findParkings = parkingRepository.findAll();

        List<ParkingResponse> responses = new ArrayList<>();
        for (Parking parking : findParkings) {
            responses.add(ParkingResponse.from(parking));
        }
        return responses;
    }

    private Point getPoint(Double longitude, Double latitude) throws ParseException {
        final String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
            Point point = (Point) new WKTReader().read(pointWKT);
            point.setSRID(WGS84_STANDARD_SRID);
            return point;

    }
}


