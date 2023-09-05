package com.notarius.urlshortener.config;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Validator for the configuration properties.
 */
public class UrlShortenerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UrlShortenerProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "urlDomainName", "required-non-empty", "URL domain name is required");
        UrlShortenerProperties properties = (UrlShortenerProperties) o;
        if (isInvalidUrl(properties.getUrlDomainName())) {
            errors.rejectValue("urlDomainName", "URL domain name has invalid format");
        }
    }

    /**
     * Validates that the given string has a correct URL format.
     *
     * @param url the url to validate
     * @return true if url is valid, false otherwise.
     */
    public boolean isInvalidUrl(String url) {
        try {
            UriComponentsBuilder.fromHttpUrl(url);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }
}
