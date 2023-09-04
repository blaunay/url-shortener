package com.notarius.urlshortener.controller;

import com.notarius.urlshortener.model.UrlData;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortenerController {

    @PostMapping("shorten")
    public String shortenUrl(@RequestBody UrlData urlData) {
        return "a5fr5";
    }

    @GetMapping("unshorten/{urlId}")
    public String unshortenUrl(@PathVariable String urlId) {
        return "longUrl";
    }
}
