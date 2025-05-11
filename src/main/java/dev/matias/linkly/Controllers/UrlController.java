package dev.matias.linkly.Controllers;

import dev.matias.linkly.domain.Url;
import dev.matias.linkly.dtos.UrlCreationDTO;
import dev.matias.linkly.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/api/url/")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/shorten/")
    public ResponseEntity<String> createShortenedUrl(@RequestBody UrlCreationDTO rawUrl){
        String url = rawUrl.originalURL().trim();

        if (!urlService.isValidUrl(url)){
            return ResponseEntity.badRequest().body(
                    "Url is Invalid or unreachable. Ensure the URL starts with https:// or http://");
        }

        String createdUrl = urlService.createShortUrl(url).getShortenedUrl();
        return ResponseEntity.ok(createdUrl);
    }
    @GetMapping("/{shortenedUrl}/")
    public RedirectView getByShortened(@PathVariable String shortenedUrl){
        Url url = urlService.getByShortenedUrl(shortenedUrl);
        return new RedirectView(url.getOriginalUrl());
    }
}
