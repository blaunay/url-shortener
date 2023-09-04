package com.notarius.urlshortener;

import com.notarius.urlshortener.config.UrlShortenerProperties;
import com.notarius.urlshortener.config.UrlShortenerValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(UrlShortenerProperties.class)
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Bean
    public static UrlShortenerValidator configurationPropertiesValidator() {
        return new UrlShortenerValidator();
    }
}
