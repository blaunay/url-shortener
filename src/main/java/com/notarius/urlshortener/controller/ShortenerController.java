package com.notarius.urlshortener.controller;

import com.notarius.urlshortener.model.UrlData;
import com.notarius.urlshortener.service.ShortenerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("shorten")
    public String shortenUrl(@RequestBody UrlData urlData) {
        // TODO trim and clean url - remove / at the end
        // TODO validate is valid url format
        return shortenerService.shortenUrl(urlData.getCompleteUrl());
    }

    @GetMapping("unshorten/{urlId}")
    public String unshortenUrl(@PathVariable String urlId) {
        return "longUrl";
    }
}
