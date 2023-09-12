package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopeu.KickboardParkBuddy.dto.ParkingResponse;
import sopeu.KickboardParkBuddy.service.ParkingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final ParkingService parkingService;

    @GetMapping("/parking")
    public List<ParkingResponse> address() throws Exception {
        return parkingService.getAllParkings();
    }
}