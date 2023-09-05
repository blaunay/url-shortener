package com.notarius.urlshortener.controller;

import com.notarius.urlshortener.config.UrlShortenerValidator;
import com.notarius.urlshortener.model.UrlData;
import com.notarius.urlshortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller layer exposing REST endpoints for URL shortening.
 */
@RestController
@RequestMapping("/url")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;
    private final UrlShortenerValidator urlShortenerValidator;

    public UrlShortenerController(UrlShortenerService urlShortenerService, UrlShortenerValidator urlShortenerValidator) {
        this.urlShortenerService = urlShortenerService;
        this.urlShortenerValidator = urlShortenerValidator;
    }

    @PostMapping("shorten")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "URL was shortened"), @ApiResponse(responseCode = "400", description = "Invalid url supplied")})
    public String shortenUrl(@Parameter(description = "Complete url to shorten") @RequestBody @NonNull UrlData urlData) {

        String completeUrl = urlData.getCompleteUrl();
        if (urlShortenerValidator.isInvalidUrl(completeUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The completeUrl field should have a valid URL format.");
        }
        return urlShortenerService.getShortenedUrl(completeUrl);
    }

    @GetMapping("unshorten/{urlId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Complete URL was returned"), @ApiResponse(responseCode = "404", description = "No URL matches this shortened URL")})
    public String unshortenUrl(@Parameter(description = "Id of the shortened URL (without the domain name)", example = "HgRF522k") @PathVariable String urlId) {

        return urlShortenerService.getCompleteUrl(urlId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The given urlId does not match any previously shortened url."));
    }
}
