package sopeu.KickboardParkBuddy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopeu.KickboardParkBuddy.service.AccidentService;
import sopeu.KickboardParkBuddy.service.parking.ParkingService;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class InitDB {

    private final ParkingService parkingService;
    private final AccidentService accidentService;

    @PostConstruct
    public void InitDBdata() throws Exception {
        parkingService.saveParkingInfo();
        accidentService.saveAccidentsInfo();
    }
}
