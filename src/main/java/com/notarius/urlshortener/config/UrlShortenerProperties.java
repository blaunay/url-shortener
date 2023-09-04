package com.notarius.urlshortener.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "shortener")
@Validated
@Data
public class UrlShortenerProperties {

    private String urlDomainName;
}
