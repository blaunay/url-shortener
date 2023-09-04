package com.notarius.urlshortener.service;

import com.notarius.urlshortener.config.UrlShortenerProperties;
import com.notarius.urlshortener.repository.UrlEntry;
import com.notarius.urlshortener.repository.UrlEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ShortenerService {

    private final UrlEntryRepository urlEntryRepository;
    private final UrlShortenerProperties urlShortenerProperties;

    public ShortenerService(UrlEntryRepository urlEntryRepository, UrlShortenerProperties urlShortenerProperties) {
        this.urlEntryRepository = urlEntryRepository;
        this.urlShortenerProperties = urlShortenerProperties;
    }

    public String shortenUrl(String completeUrl) {
        String urlId = "jghyy4brk"; // TODO apply function to shorten
        UrlEntry urlEntry = urlEntryRepository.findById(urlId).orElse(saveNewUrlEntry(urlId, completeUrl));
        return buildShortenedUrl(urlEntry);
    }

    private String buildShortenedUrl(UrlEntry urlEntry) {
        return UriComponentsBuilder.fromHttpUrl(urlShortenerProperties.getUrlDomainName()).path(urlEntry.getShortenedUrlId()).build().toUriString();
    }

    private UrlEntry saveNewUrlEntry(String urlId, String completeUrl) {
        UrlEntry urlEntry = new UrlEntry(urlId, completeUrl);
        return urlEntryRepository.save(urlEntry);
    }
}
