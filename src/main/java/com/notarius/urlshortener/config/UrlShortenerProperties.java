package com.notarius.urlshortener.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the url shortener app.
 */
@ConfigurationProperties(prefix = "shortener")
@Validated
@Data
public class UrlShortenerProperties {

    /**
     * URL domain name to be used in the shortened URL
     */
    private String urlDomainName;
}
