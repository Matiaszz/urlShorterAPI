package dev.matias.linkly.repository;

import dev.matias.linkly.domain.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UrlRepository extends MongoRepository<Url, UUID> {
    Optional<Url> findByShortenedUrl(String shortenedUrl);
}
