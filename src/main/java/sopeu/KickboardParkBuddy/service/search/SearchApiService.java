package sopeu.KickboardParkBuddy.service.search;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchRequest;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class SearchApiService {
    private final String uri = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private final String kakaoLocalKey = URLDecoder.decode("f45f27c7b49797090f7f8c6cc60d0a2e", StandardCharsets.UTF_8);

    public KeywordSearchResponse getKeywordSearch(KeywordSearchRequest request) {
        RestTemplate restTemplate = new RestTemplate();  //api 요청을 보내기 위해 spring에서 제공하는 restTemplate를 사용

        String apiKey = "KakaoAK " + kakaoLocalKey;

        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("query", request.getKeyword())
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        // body에서 좌표 뽑아내기
        JSONArray documents = json.getJSONArray("documents");
        String placeName = documents.getJSONObject(0).getString("place_name");
        String address = documents.getJSONObject(0).getString("road_address_name");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");


        return new KeywordSearchResponse(placeName, address, x, y);
    }
}
