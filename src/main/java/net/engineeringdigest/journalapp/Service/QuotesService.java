package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.Entity.Quotes;
import net.engineeringdigest.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class QuotesService {
   // private final AppCache cache;
    private final WebClient webClient;
    public QuotesService(AppCache cache, WebClient.Builder webClientBuilder, @Value("${quotes_api_key}") String apiKey) {
//        this.cache = cache;
        this.webClient = webClientBuilder.baseUrl(cache.appCache.get(AppCache.apis.QUOTES_API.toString()))
                .defaultHeader("x-api-key",apiKey).build();
    }


    //String url= "https://api.api-ninjas.com";

    //RestTemplate restTemplate=new RestTemplate();

    public Quotes getQuotes(){
        Quotes[] body = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/randomquotes")
                        .queryParam("categories", "success,wisdom")
                        .build())
                .retrieve()
                .bodyToMono(Quotes[].class)
                .block();

//        String Url= UriComponentsBuilder
//                .fromUriString("https://api.api-ninjas.com")
//                .path("/v2/randomquotes")
//                .queryParam("categories","success,wisdom")
//                .build()
//                .toUriString();
//        HttpHeaders headers=new HttpHeaders();
//        headers.set("X-Api-Key", apiKey);
//        HttpEntity<String> entity=new HttpEntity<>(headers);
//        Quotes[] body = restTemplate.exchange(Url, HttpMethod.GET, entity, Quotes[].class).getBody();
        return body[0];
    }
}
