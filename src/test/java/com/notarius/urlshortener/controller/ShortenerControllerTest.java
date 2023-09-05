package com.notarius.urlshortener.controller;

import com.notarius.urlshortener.config.UrlShortenerValidator;
import com.notarius.urlshortener.model.UrlData;
import com.notarius.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenerControllerTest {

    private static final String COMPLETE_URL = "http://huge-url.ca/tests";
    private static final String SHORTENED_ID = "Ab";
    private static final String SHORTENED_URL = "http://mini.ca/" + SHORTENED_ID;
    @InjectMocks
    private UrlShortenerController urlShortenerController;
    @Mock
    private UrlShortenerService urlShortenerService;
    @Mock
    private UrlShortenerValidator urlShortenerValidator;

    @Test
    void givenInvalidUrl_whenShortenUrl_thenExceptionIsThrown() {
        when(urlShortenerValidator.isInvalidUrl(eq(COMPLETE_URL))).thenReturn(true);

        ResponseStatusException throwable = catchThrowableOfType(() -> urlShortenerController.shortenUrl(new UrlData(COMPLETE_URL)), ResponseStatusException.class);

        assertThat(throwable.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void givenValidUrl_whenShortenUrl_thenShortenedUrlIsReturned() {
        when(urlShortenerValidator.isInvalidUrl(eq(COMPLETE_URL))).thenReturn(false);
        when(urlShortenerService.getShortenedUrl(eq(COMPLETE_URL))).thenReturn(SHORTENED_URL);

        String shortenedUrl = urlShortenerController.shortenUrl(new UrlData(COMPLETE_URL));

        assertThat(shortenedUrl).isEqualTo(SHORTENED_URL);
    }

    @Test
    void givenKnownUrl_whenUnshortenUrl_thenCompleteUrlIsReturned() {
        when(urlShortenerService.getCompleteUrl(eq(SHORTENED_ID))).thenReturn(Optional.of(COMPLETE_URL));

        String completeUrl = urlShortenerController.unshortenUrl(SHORTENED_ID);

        assertThat(completeUrl).isEqualTo(COMPLETE_URL);
    }

    @Test
    void givenUnknownUrl_whenUnshortenUrl_thenExceptionIsThrown() {
        when(urlShortenerService.getCompleteUrl(eq(SHORTENED_ID))).thenReturn(Optional.empty());

        ResponseStatusException throwable = catchThrowableOfType(() -> urlShortenerController.unshortenUrl(SHORTENED_ID), ResponseStatusException.class);

        assertThat(throwable.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}