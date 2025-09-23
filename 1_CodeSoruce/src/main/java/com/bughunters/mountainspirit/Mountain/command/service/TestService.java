package com.bughunters.mountainspirit.Mountain.command.service;

import com.bughunters.mountainspirit.Mountain.command.dto.ResponseApiMountainDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestService {

    private WebClient webClient;
    private ModelMapper modelMapper;

    @Value("${govermentData.serviceKey}")
    private String serviceKey;
    @Value("${govermentData.path.peakPoint}")
    private String pathString;

    @Autowired
    public TestService(WebClient webClient, ModelMapper modelMapper) {
        this.webClient = webClient;
        this.modelMapper = modelMapper;
    }



    @GetMapping("/test123")
    public void test() {
        System.out.println("serviceKey = " + serviceKey);
        System.out.println("pathString = " + pathString);

        final int size = 100;
        int total = fetchTotalCount();  //데이터 총 개수 조회
        int pages = (total + size - 1) / size;
        int saved = 0;
        for (int i = 1; i <= pages; i++) {
            List<ResponseApiMountainDTO> items = fetchItems(i, size); // 100개 단위로 데이터 조회
            
            // Entity로 변환 및 save 로직 추가 예정
        }
    }

    // 1. totalCount만 조회
    public int fetchTotalCount() {
        // 1. 총 데이터수만(totalCount) 받아오기 위해 page : 1 , pageSize : 0
        URI uri = buildUri(1,0);
        JsonNode root = webClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block(); // 이벤트 루프가 아닌 곳에서 호출

        return root == null ? 0
                : root.path("response").path("body").path("totalCount").asInt(0);
    }

    // 2. 페이지 단위로 items.item만 DTO 리스트로
    public List<ResponseApiMountainDTO> fetchItems(int pageNo, int size) {

        URI uri = buildUri(pageNo, size);
        JsonNode root = webClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode arr = root == null
                ? null
                : root.path("response").path("body").path("items").path("item");

        List<ResponseApiMountainDTO> list = new ArrayList<>();
        if (arr != null && arr.isArray()) {
            for (JsonNode n : arr) {
                list.add(modelMapper.map(n, ResponseApiMountainDTO.class));
            }
        }
        return list;
    }


    private URI buildUri(int pageNo, int pageSize) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(pathString)
                .queryParam("numOfRows", pageSize)
                .queryParam("pageNo", pageNo)
                .queryParam("serviceKey", serviceKey)
                .queryParam("type", "json")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return uri;
    }
}
