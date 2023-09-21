package sopeu.KickboardParkBuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopeu.KickboardParkBuddy.dto.ParkingResultDto;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchRequest;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchResponse;
import sopeu.KickboardParkBuddy.dto.search.SearchRequest;
import sopeu.KickboardParkBuddy.service.search.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public List<ParkingResultDto> searching(@RequestParam(name = "x")String x, @RequestParam(name = "y")String y) throws Exception {  //내 위치로 검색
        SearchRequest searchRequest = new SearchRequest(x, y);
        return searchService.searchParking(searchRequest);
    }

    @GetMapping("/search/keyword")
    public KeywordSearchResponse keywordSearch(@RequestParam(name = "keyword")String keyword) {
        KeywordSearchRequest keywordSearchRequest = new KeywordSearchRequest(keyword);
        return searchService.keywordSearch(keywordSearchRequest);
    }
}
