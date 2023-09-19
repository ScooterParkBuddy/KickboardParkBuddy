package sopeu.KickboardParkBuddy.service.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopeu.KickboardParkBuddy.dto.ParkingResultDto;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchRequest;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchResponse;
import sopeu.KickboardParkBuddy.dto.search.SearchRequest;
import sopeu.KickboardParkBuddy.repository.ParkingRepository;
import sopeu.KickboardParkBuddy.service.search.SearchApiService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final ParkingRepository parkingRepository;
    private final SearchApiService searchApiService;
    @PersistenceContext
    private EntityManager em;

    public List<ParkingResultDto> searchParking(SearchRequest searchRequest) {
        double lng = Double.parseDouble(searchRequest.getX());
        double lat = Double.parseDouble(searchRequest.getY());

        List<Object[]> rawResults = parkingRepository.searchParking(lng, lat);

        List<ParkingResultDto> results = new ArrayList<>();
        for (Object[] row : rawResults) {
            BigInteger bigIntegerId = (BigInteger) row[0];
            Long parkingId = bigIntegerId.longValue();

            byte[] locationBytes = (byte[]) row[2];
            String locationString = new String(locationBytes, StandardCharsets.UTF_8);
            Double longitudeDouble = (Double) row[3];
            String longitudeString = longitudeDouble.toString();

            Double latitudeDouble = (Double) row[4];
            String latitudeString = latitudeDouble.toString();

            Double distance = (Double) row[6];

            results.add(new ParkingResultDto(
                    parkingId,
                    (String) row[1],
                    locationString,
                    longitudeString,
                    latitudeString,
                    (String) row[5],
                    distance
            ));
        }
        return results;
    }

    public KeywordSearchResponse keywordSearch(KeywordSearchRequest keywordSearchRequest) {
        return searchApiService.getKeywordSearch(keywordSearchRequest);
    }


}
