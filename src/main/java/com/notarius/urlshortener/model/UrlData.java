package com.notarius.urlshortener.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class UrlData {
    @NonNull
    private String completeUrl;
}
