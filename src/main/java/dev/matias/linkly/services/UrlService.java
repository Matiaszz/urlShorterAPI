package dev.matias.linkly.services;

import dev.matias.linkly.domain.Url;
import dev.matias.linkly.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final RandomStringUtils randomStringUtils;

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
}
