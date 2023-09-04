package com.notarius.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlEntryRepository extends CrudRepository<UrlEntry, String> {
    Optional<UrlEntry> findById(@NonNull String shortenedUrlId);
}
