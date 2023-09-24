package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopeu.KickboardParkBuddy.dto.accident.AccidentResponse;
import sopeu.KickboardParkBuddy.service.AccidentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping("/accident")
    public List<AccidentResponse> accident() {
        return accidentService.getAllAccidents();
    }

}
