package com.notarius.urlshortener.service;

import com.notarius.urlshortener.config.UrlShortenerProperties;
import com.notarius.urlshortener.repository.UrlEntry;
import com.notarius.urlshortener.repository.UrlEntryRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlEntryRepository urlEntryRepository;
    private final UrlShortenerProperties urlShortenerProperties;

    public UrlShortenerService(UrlEntryRepository urlEntryRepository, UrlShortenerProperties urlShortenerProperties) {
        this.urlEntryRepository = urlEntryRepository;
        this.urlShortenerProperties = urlShortenerProperties;
    }

    /**
     * Shortens a complete URL
     *
     * @param completeUrl the URL to shorten
     * @return the shortened URL
     */
    public String getShortenedUrl(@NonNull String completeUrl) {
        String urlId = computeShortenedUrlId(completeUrl);
        UrlEntry urlEntry = urlEntryRepository.findById(urlId).orElseGet(() -> saveNewUrlEntry(urlId, completeUrl));
        return buildShortenedUrl(urlEntry);
    }

    /**
     * Retrieves the complete URL, from a given shortened URL id
     *
     * @param urlId the shortened URL id, without the domain name
     * @return an Optional containing the complete URL, if it exists
     */
    public Optional<String> getCompleteUrl(@NonNull String urlId) {
        return urlEntryRepository.findById(urlId).map(UrlEntry::getCompleteUrl);
    }

    /**
     * Using the complete URL, returns the id that will be used in the shortened URL.
     * The complete URL is hashed using MD5, then encoded using the <a href="https://datatracker.ietf.org/doc/html/rfc4648">Base64 URL-safe standard</a>
     * The first 10 digits are used as the id
     *
     * @param completeUrl The complete URL to shorten
     * @return The id to be used in the shortened URL.
     */
    private String computeShortenedUrlId(@NonNull String completeUrl) {
        byte[] md5HashedUrl = DigestUtils.md5Digest(completeUrl.trim().getBytes());
        String encodedUrl = new String(Base64.getUrlEncoder().encode(md5HashedUrl));
        //could be problematic if two different url give the same Id, but should be extremely rare
        return encodedUrl.substring(0, 10);
    }

    private String buildShortenedUrl(UrlEntry urlEntry) {
        return UriComponentsBuilder.fromHttpUrl(urlShortenerProperties.getUrlDomainName()).path(urlEntry.getShortenedUrlId()).build().toUriString();
    }

    private UrlEntry saveNewUrlEntry(String urlId, String completeUrl) {
        UrlEntry urlEntry = new UrlEntry(urlId, completeUrl);
        return urlEntryRepository.save(urlEntry);
    }
}
