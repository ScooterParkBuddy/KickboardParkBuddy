package sopeu.KickboardParkBuddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import sopeu.KickboardParkBuddy.dto.EstimateResultDto;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchRequest;
import sopeu.KickboardParkBuddy.dto.search.KeywordSearchResponse;
import sopeu.KickboardParkBuddy.service.search.SearchService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingDataService {
    private WebDriver driver;
    private WebElement element;
    private final SearchService searchService;
    private static final String url = "https://map.kakao.com/link/to/";

    //private static final String url = "https://map.naver.com/v5/search";
    public EstimateResultDto process(String start, String destination) throws InterruptedException {

        KeywordSearchRequest keywordSearchRequest = new KeywordSearchRequest(destination);
        KeywordSearchResponse keywordSearchResponse = searchService.keywordSearch(keywordSearchRequest);
        log.info(keywordSearchResponse.toString());
        String placeName = keywordSearchResponse.getPlaceName();
        String lng = keywordSearchResponse.getLng();  //경도
        String lat = keywordSearchResponse.getLat();   //위도


        String keyword = placeName+","+lat+","+lng;
        String realUrl = url + keyword;
        // 크롬 드라이버 세팅 (드라이버 설치 경로 입력)
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--single-process");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");


        // 브라우저 선택
        driver = new ChromeDriver(options);

        EstimateResultDto data = getDataList(realUrl, start);

        // 탭 닫기
        driver.close();
        // 브라우저 닫기
        driver.quit();
        return data;
    }

    // 데이터 가져오기
    private EstimateResultDto getDataList(String url, String start) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        // (1) 브라우저에서 url로 이동한다.
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        element = driver.findElement(By.id("info.route.waypointSuggest.input0"));
        element.sendKeys(start);
        element.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        int screenWidth = driver.manage().window().getSize().getWidth();
        int screenHeight = driver.manage().window().getSize().getHeight();

        // Calculate the center of the screen
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // Use Actions class to move to the center and click
        Actions actions = new Actions(driver);
        actions.moveByOffset(centerX, centerY).click().perform();


        //element = driver.findElement(By.id("biketab"));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"biketab\"]")));
        element.click();

        Thread.sleep(1000);

        //거리
        WebElement element1 = driver.findElement(By.xpath("//*[@id=\"info.bikeRoute\"]/div[1]/ul/li[1]/div[1]/div[1]/p/span[2]"));
        String dis = element1.getText();

        //시간
        WebElement element2 = driver.findElement(By.xpath("//*[@id=\"info.bikeRoute\"]/div[1]/ul/li[1]/div[1]/div[1]/p/span[1]"));
        String time = element2.getText();

        //고도
        WebElement element3 = driver.findElement(By.xpath("//*[@id=\"info.bikeRoute\"]/div[1]/ul/li[1]/div[1]/div[1]/div/span[1]"));
        String altitude = element3.getText();

        return new EstimateResultDto(dis, time, altitude);
    }
}

