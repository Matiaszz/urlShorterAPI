package dev.matias.linkly.services;

import dev.matias.linkly.domain.Url;
import dev.matias.linkly.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    private final RestTemplate restTemplate;

    public Url createShortUrl(String originalUrl){
        String newUrl = RandomStringUtils.randomAlphanumeric(6);

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortenedUrl(newUrl)
                .createdAt(LocalDateTime.now().toString())
                .build();

        return urlRepository.save(url);
    }

    public Url getByShortenedUrl(String shortenedUrl){
        return urlRepository.findByShortenedUrl(shortenedUrl).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortened URL not found"));
    }

    public boolean isValidUrl(String url){
        if (!isWellFormedUrl(url)){
            return false;
        }
        return true;

//        try{
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            return response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection();
//
//        } catch (RestClientException e){
//            return false;
//        }
    }

    private boolean isWellFormedUrl(String url) {
        try {
            URI uri = URI.create(url);
            String scheme = uri.getScheme();
            return scheme != null && (scheme.equals("http") || scheme.equals("https")) && uri.getHost() != null;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
