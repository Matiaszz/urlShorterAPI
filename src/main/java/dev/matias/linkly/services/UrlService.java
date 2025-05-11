package dev.matias.linkly.services;

import dev.matias.linkly.domain.Url;
import dev.matias.linkly.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private final LocalDateTime localDateTime;

    public Url createShortUrl(String originalUrl){
        String newUrl = RandomStringUtils.randomAlphanumeric(6);

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortenedUrl(newUrl)
                .createdAt(LocalDateTime.now())
                .expirationMinutes(43200) // 1 month
                .build();

        return urlRepository.save(url);
    }

    public Url getByShortenedUrl(String shortenedUrl){
        return urlRepository.findByShortenedUrl(shortenedUrl).filter(obj -> !obj.isExpired()) .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortened URL not found"));
    }

    public boolean isValidUrl(String url){
        return isWellFormedUrl(url);
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
