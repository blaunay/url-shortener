package com.notarius.urlshortener.model;

import lombok.Data;

@Data
public class UrlData {
    private String completeUrl;
    private String shortenedUrl;
}
