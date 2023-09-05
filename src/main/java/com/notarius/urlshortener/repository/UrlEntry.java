package com.notarius.urlshortener.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Persisted entity matching a complete URL with the id used in the shortened URL.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntry {
    @Id
    private String shortenedUrlId;
    private String completeUrl;
}
