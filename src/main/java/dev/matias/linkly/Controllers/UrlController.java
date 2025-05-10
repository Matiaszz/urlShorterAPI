package dev.matias.linkly.Controllers;

import dev.matias.linkly.dtos.UrlCreationDTO;
import dev.matias.linkly.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> createShortenedUrl(@RequestBody UrlCreationDTO rawUrl){

        String url = rawUrl.originalURL().trim();

        if (!urlService.isValidUrl(url)){
            return ResponseEntity.badRequest().body(
                    "Url is Invalid or unreachable. Ensure the URL starts with https:// or http://");
        }

        String createdUrl = urlService.createShortUrl(url).getShortenedUrl();
        return ResponseEntity.ok(createdUrl);
    }


}
