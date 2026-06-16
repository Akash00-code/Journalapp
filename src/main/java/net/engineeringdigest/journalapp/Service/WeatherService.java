package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.Entity.WeatherResponse;
import net.engineeringdigest.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {
    @Value("${weather_api_key}")
    String apiKey;
    //private final AppCache cache;
    private final WebClient webClient;
    public WeatherService(AppCache cache, WebClient.Builder webClientBuilder) {
        //this.cache = cache;
        this.webClient = webClientBuilder.baseUrl(cache.appCache.get(AppCache.apis.WEATHER_API.toString())).build();
    }


    //String url="http://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=CITY";

    //private final RestTemplate restTemplate = new RestTemplate();


    public WeatherResponse getCurrentWeather(String city){
//        String Url = url.replace("YOUR_ACCESS_KEY", apiKey).replace("CITY", city);
//        return restTemplate.exchange(Url, HttpMethod.GET, null, WeatherResponse.class).getBody();

        return webClient.get()
                .uri(uribuilder->uribuilder
                        .path("/current")
                        .queryParam("access_key",apiKey)
                        .queryParam("query",city)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();
    }
}
