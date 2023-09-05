package com.notarius.urlshortener.service;

import com.notarius.urlshortener.config.UrlShortenerProperties;
import com.notarius.urlshortener.repository.UrlEntry;
import com.notarius.urlshortener.repository.UrlEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {
    private static final String COMPLETE_URL = "http://huge-url.ca/tests";
    private static final String SHORTENED_ID = "AAg531";
    private static final String URL_DOMAIN_NAME = "http://mini.io";

    @InjectMocks
    private UrlShortenerService urlShortenerService;
    @Mock
    private UrlEntryRepository urlEntryRepository;
    @Mock
    private UrlShortenerProperties urlShortenerProperties;

    @Test
    void givenUnknownUrlId_whenGetCompleteUrl_thenReturnsEmpty() {
        when(urlEntryRepository.findById(eq(SHORTENED_ID))).thenReturn(Optional.empty());

        Optional<String> completeUrl = urlShortenerService.getCompleteUrl(SHORTENED_ID);

        assertThat(completeUrl).isEmpty();
    }

    @Test
    void givenKnownUrlId_whenGetCompleteUrl_thenReturnsURL() {
        when(urlEntryRepository.findById(eq(SHORTENED_ID))).thenReturn(Optional.of(new UrlEntry(SHORTENED_ID, COMPLETE_URL)));

        Optional<String> completeUrl = urlShortenerService.getCompleteUrl(SHORTENED_ID);

        assertThat(completeUrl).isPresent();
        assertThat(completeUrl.get()).isEqualTo(COMPLETE_URL);
    }

    @Test
    void givenKnownUrl_whenGetShortenedUrl_thenReturnsShortenedUrl() {
        when(urlEntryRepository.findById(anyString())).thenReturn(Optional.of(new UrlEntry(SHORTENED_ID, COMPLETE_URL)));
        when(urlShortenerProperties.getUrlDomainName()).thenReturn(URL_DOMAIN_NAME);

        String shortenedUrl = urlShortenerService.getShortenedUrl(COMPLETE_URL);

        assertThat(shortenedUrl).isEqualTo(URL_DOMAIN_NAME + "/" + SHORTENED_ID);
        verify(urlEntryRepository, never()).save(any());
    }

    @Test
    void givenUnknownUrl_whenGetShortenedUrl_thenReturnsShortenedUrl() {
        ArgumentCaptor<UrlEntry> argument = ArgumentCaptor.forClass(UrlEntry.class);
        when(urlEntryRepository.findById(anyString())).thenReturn(Optional.empty());
        when(urlShortenerProperties.getUrlDomainName()).thenReturn(URL_DOMAIN_NAME);
        when(urlEntryRepository.save(any())).thenReturn(new UrlEntry(SHORTENED_ID, COMPLETE_URL));

        String shortenedUrl = urlShortenerService.getShortenedUrl(COMPLETE_URL);

        assertThat(shortenedUrl).isEqualTo(URL_DOMAIN_NAME + "/" + SHORTENED_ID);
        verify(urlEntryRepository).save(argument.capture());
        assertThat(argument.getValue().getShortenedUrlId()).isNotBlank();
        assertThat(argument.getValue().getCompleteUrl()).isEqualTo(COMPLETE_URL);
    }
}