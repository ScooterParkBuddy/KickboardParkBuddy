package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopeu.KickboardParkBuddy.dto.EstimateResultDto;
import sopeu.KickboardParkBuddy.service.CrawlingDataService;


@RestController
@RequiredArgsConstructor
public class EstimateController {

    private final CrawlingDataService crawlingDataService;

    @GetMapping("/estimate")
    public EstimateResultDto predictPriceTime(@RequestParam("start")String start, @RequestParam("destination")String destination ) throws Exception {
        return crawlingDataService.process(start, destination);
    }
}
