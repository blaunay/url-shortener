package com.notarius.urlshortener.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlEntryRepository extends CrudRepository<UrlEntry, String> {
    Optional<UrlEntry> findById(@NonNull String shortenedUrlId);
}
