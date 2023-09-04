package com.notarius.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

class ShortenerServiceTest {

    @Test
    public void test() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://google.ca/hbfekhbrgfkg?hjbjkhb=k");
        System.out.println(uriComponentsBuilder.build().getPath());
    }

}