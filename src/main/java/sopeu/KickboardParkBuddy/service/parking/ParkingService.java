package sopeu.KickboardParkBuddy.service.parking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.dto.Coordinates;
import sopeu.KickboardParkBuddy.domain.Parking;
import sopeu.KickboardParkBuddy.dto.ParkingResponse;
import sopeu.KickboardParkBuddy.repository.ParkingRepository;

import java.io.*;
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


    @Transactional
    public void saveParkingInfo() throws Exception {
        JSONParser parser = new JSONParser();

        // 클래스패스 상의 리소스를 가져옵니다.
        Resource resource = new ClassPathResource("parkingData.json");

        // 리소스로부터 InputStream을 얻어옵니다.
        InputStream inputStream = resource.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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


