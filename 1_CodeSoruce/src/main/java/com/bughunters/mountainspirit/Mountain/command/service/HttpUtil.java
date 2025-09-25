package com.bughunters.mountainspirit.Mountain.command.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpUtil {


    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private WebClient webClient;

    @Value("${govermentData.serviceKey}")
    private String serviceKey;

    public HttpUtil(ModelMapper modelMapper, ObjectMapper objectMapper, WebClient webClient) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    // 1. totalCount만 조회
    public int fetchTotalCount(String urlPath) {
        // 1. 총 데이터수만(totalCount) 받아오기 위해 page : 1 , pageSize : 0
        URI uri = buildUri(1,0,urlPath);
        JsonNode root = webClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block(); // 블로킹 함수

        return root == null ? 0
                : root.path("response").path("body").path("totalCount").asInt(0);
    }

    // 2. 페이지 단위로 items.item만 DTO 리스트로
    public <D> List<D> fetchItems(int pageNo,
                                                    int size,
                                                    String urlPath,
                                                    Class<D> destType ) {

        URI uri = buildUri(pageNo, size, urlPath);
        JsonNode root = webClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode itemsNode = root == null
                ? null
                : root.path("response").path("body").path("items").path("item");

        List<D> list = new ArrayList<>();
        if (itemsNode == null || itemsNode.isMissingNode() || itemsNode.isNull()) {
            return list;
        }

        // 공공데이터 API가 item을 1개일 때 '배열'이 아니라 '객체'로 주는 경우가 있음 → 둘 다 처리
        if (itemsNode.isArray()) {
            for (JsonNode n : itemsNode) {
                try {
                    list.add(objectMapper.treeToValue(n, destType));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (itemsNode.isObject()) {
            try {
                list.add(objectMapper.treeToValue(itemsNode, destType));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }


    private URI buildUri(int pageNo, int pageSize, String urlPath) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(urlPath)
                .queryParam("numOfRows", pageSize)
                .queryParam("pageNo", pageNo)
                .queryParam("serviceKey", serviceKey)
                .queryParam("type", "json")
//                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return uri;
    }
}
