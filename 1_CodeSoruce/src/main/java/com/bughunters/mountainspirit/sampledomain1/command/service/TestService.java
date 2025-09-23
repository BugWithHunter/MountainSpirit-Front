package com.bughunters.mountainspirit.sampledomain1.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
public class TestService {

    private WebClient webClient;
    @Value("${govermentData.serviceKey}")
    private String serviceKey;
    @Value("${govermentData.path.peakPoint}")
    private String pathString;

    @Autowired
    public TestService(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/test123")
    public Mono<String> test() {
        System.out.println("serviceKey = " + serviceKey);
        System.out.println("pathString = " + pathString);
//        URI uri = UriComponentsBuilder
//                .fromHttpUrl(pathString)
//                .queryParam("numOfRows", 100)
//                .queryParam("pageNo", 1)
//                .queryParam("serviceKey", rawKey)     // …==
//                .queryParam("srchFrtrlNm", "관악산")
//                .queryParam("type", "json")
//                .encode(StandardCharsets.UTF_8)
//                .build()
//                .toUri();



        System.out.println("webClient = " + webClient);
        Mono<String> response = webClient.get()
                .uri("/peakPoiInfoService/getPeakPoiInfoList?numOfRows=100&pageNo=1&serviceKey=7a162b2698e08c06f13a25c9fe96fd89c1788751a7d3adc46918921e9ee2e2a5&type=json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(body -> System.out.println("[RAW] " + body)); // 디버깅용;

//        response.subscribe(System.out::println);

        return response;
    }
}
