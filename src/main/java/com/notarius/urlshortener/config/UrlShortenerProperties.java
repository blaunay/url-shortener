package com.notarius.urlshortener.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shortener")
@Data
public class UrlShortenerProperties {

    //TODO validate format of url
    private String urlDomainName;
}
