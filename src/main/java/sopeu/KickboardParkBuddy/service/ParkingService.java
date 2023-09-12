package sopeu.KickboardParkBuddy.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.domain.Coordinates;
import sopeu.KickboardParkBuddy.domain.Parking;
import sopeu.KickboardParkBuddy.dto.ParkingResponse;
import sopeu.KickboardParkBuddy.repository.ParkingRepository;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingApiService parkingApiService;
    private final ParkingRepository parkingRepository;
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
            Double lat = Double.parseDouble(coordinate.getX());
            Double lng = Double.parseDouble(coordinate.getY());

            Parking parkingInfo = new Parking(placeName, address, lat, lng);
            parkingRepository.save(parkingInfo);
        }
    }

    public List<ParkingResponse> getAllParkings(){
        List<Parking> findParkings = parkingRepository.findAll();

        List<ParkingResponse> responses = new ArrayList<>();
        for (Parking parking : findParkings) {
            responses.add(ParkingResponse.from(parking));
        }
        return responses;
    }
}


