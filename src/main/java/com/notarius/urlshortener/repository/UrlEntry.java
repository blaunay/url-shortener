package com.notarius.urlshortener.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntry {
    @Id
    private String shortenedUrlId;
    private String completeUrl;
}
