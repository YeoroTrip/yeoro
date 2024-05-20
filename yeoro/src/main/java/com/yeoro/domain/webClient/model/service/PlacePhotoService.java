package com.yeoro.domain.webClient.model.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PlacePhotoService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public PlacePhotoService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<String> saveAndGetPhotoUrl(String photoName) {
        if (photoName == null || photoName.isEmpty()) {
            return Mono.empty();
        }

        String apiKey = "AIzaSyDKjpvXazGFn3UTiwPySnSxORVfOEu-NoI"; // 본인의 API 키로 교체해주세요
        int maxHeightPx = 400;
        int maxWidthPx = 400;

        String url = String.format("/%s/media?maxHeightPx=%d&maxWidthPx=%d&key=%s", photoName, maxHeightPx, maxWidthPx, apiKey);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class) // 문자열 형태로 반환
                .map(this::extractPhotoUri); // photoUri 추출
    }

    private String extractPhotoUri(String responseBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("photoUri").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
