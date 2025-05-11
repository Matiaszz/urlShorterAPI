package dev.matias.linkly.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @MongoId
    private String id;

    private String originalUrl;
    private String shortenedUrl;
    private LocalDateTime createdAt;
    private long expirationMinutes;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(expirationMinutes));
    }
}
