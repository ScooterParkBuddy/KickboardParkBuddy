package sopeu.KickboardParkBuddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.locationtech.jts.geom.Point;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.domain.Accident;
import sopeu.KickboardParkBuddy.dto.accident.AccidentResponse;
import sopeu.KickboardParkBuddy.repository.AccidentRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private static final int WGS84_STANDARD_SRID = 4_326;

//    @PostConstruct
    @Transactional
    public void saveAccidentsInfo() throws Exception {
        JSONParser parser = new JSONParser();

        // 클래스패스 상의 리소스를 가져옵니다.
        Resource resource = new ClassPathResource("accident.json");

        // 리소스로부터 InputStream을 얻어옵니다.
        InputStream inputStream = resource.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            JSONArray dateArray = (JSONArray) parser.parse(reader);

            for (Object obj : dateArray) {
                JSONObject element = (JSONObject) obj;
                Double longitude = (Double) element.get("LNG");
                Double latitude = (Double) element.get("LAT");
                Long danger = (Long) element.get("DANGER");

                Accident accidentInfo = new Accident(longitude, latitude, danger);
                accidentRepository.save(accidentInfo);
            }
        }
    }

    public List<AccidentResponse> getAllAccidents() {
        List<Accident> findAccidents = accidentRepository.findAll();

        List<AccidentResponse> responses = new ArrayList<>();
        for (Accident accident : findAccidents) {
            responses.add(AccidentResponse.from(accident));
        }
        return responses;
    }

}
