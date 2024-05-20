package com.yeoro.domain.webClient.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoro.domain.place.model.dto.PlaceDto;
import com.yeoro.domain.placeDetail.model.dto.PlaceDetailDto;
import com.yeoro.domain.placeOpeningHours.model.dto.PlaceOpeningHoursDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceSearchService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public PlaceSearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://places.googleapis.com/v1").build();
        this.objectMapper = objectMapper;
    }

    public Mono<List<PlaceDto>> searchPlaces(String textQuery, double latitude, double longitude) {
        String apiKey = "AIzaSyDKjpvXazGFn3UTiwPySnSxORVfOEu-NoI"; // 본인의 API 키로 교체해주세요

        String url = "/places:searchText?key=" + apiKey;

        String requestBody = String.format(
                "{\"textQuery\": \"%s\", \"locationBias\": {\"circle\": {\"center\": {\"latitude\": %f, \"longitude\": %f}, \"radius\": %f}}, \"languageCode\": \"%s\", \"pageSize\": %d}",
                textQuery, latitude, longitude, 500.0, "ko", 10
        );

        return webClient.post()
                .uri(url)
                .header("X-Goog-Api-Key", apiKey)
                .header("X-Goog-FieldMask",
                        "places.id,places.displayName.text,places.formattedAddress,places.location,places.primaryTypeDisplayName,places.nationalPhoneNumber,places.rating,places.photos,places.currentOpeningHours.weekdayDescriptions,places.types")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::convertToPlaceDtos);
    }

    private List<PlaceDto> convertToPlaceDtos(JsonNode jsonNode) {
        List<PlaceDto> placeDtos = new ArrayList<>();
        JsonNode placesNode = jsonNode.get("places");

        if (placesNode != null && placesNode.isArray()) {
            for (JsonNode placeNode : placesNode) {
                String googleId = placeNode.has("id") ? placeNode.get("id").asText() : "";
                String name = placeNode.has("displayName")
                        && placeNode.get("displayName").has("text")
                        ? placeNode.get("displayName").get("text").asText()
                        : "";
                String fullAddress = placeNode.has("formattedAddress") ? placeNode.get("formattedAddress").asText() : "";
                Double latitude = placeNode.has("location") && placeNode.get("location").has("latitude") ? placeNode.get("location").get("latitude").asDouble() : 0.0;
                Double longitude = placeNode.has("location") && placeNode.get("location").has("longitude") ? placeNode.get("location").get("longitude").asDouble() : 0.0;
                String category = placeNode.has("primaryTypeDisplayName") ? placeNode.get("primaryTypeDisplayName").asText() : "";
                String phoneNumber = placeNode.has("nationalPhoneNumber") ? placeNode.get("nationalPhoneNumber").asText() : "";
                Double rating = placeNode.has("rating") ? placeNode.get("rating").asDouble() : 0.0;
                String photo = placeNode.has("photos") && placeNode.get("photos").isArray() && placeNode.get("photos").size() > 0
                        && placeNode.get("photos").get(0).has("name") ? placeNode.get("photos").get(0).get("name").asText() : "";
                JsonNode weekdayDescriptions = (placeNode.has("currentOpeningHours")
                        && placeNode.get("currentOpeningHours").has("weekdayDescriptions"))
                        ? placeNode.get("currentOpeningHours").get("weekdayDescriptions")
                        : null;

                PlaceDetailDto placeDetailDto = PlaceDetailDto.builder()
                        .name(name)
                        .fullAddress(fullAddress)
                        .latitude(latitude)
                        .longitude(longitude)
                        .category(category)
                        .phoneNumber(phoneNumber)
                        .rating(rating)
                        .photo(photo)
                        .placeOpeningHoursDto(createPlaceOpeningHoursDto(weekdayDescriptions))
                        .build();

                PlaceDto placeDto = PlaceDto.builder()
                        .googleId(googleId)
                        .placeDetailDto(placeDetailDto)
                        .build();

                placeDtos.add(placeDto);
            }
        }

        return placeDtos;
    }

    private PlaceOpeningHoursDto createPlaceOpeningHoursDto(JsonNode weekdayDescriptions) {
        if (weekdayDescriptions == null || !weekdayDescriptions.isArray()) {
            return PlaceOpeningHoursDto.builder()
                    .sunday("해당 매장에서 정보를 제공하지 않습니다")
                    .monday("해당 매장에서 정보를 제공하지 않습니다")
                    .tuesday("해당 매장에서 정보를 제공하지 않습니다")
                    .wednesday("해당 매장에서 정보를 제공하지 않습니다")
                    .thursday("해당 매장에서 정보를 제공하지 않습니다")
                    .friday("해당 매장에서 정보를 제공하지 않습니다")
                    .saturday("해당 매장에서 정보를 제공하지 않습니다")
                    .build();
        }

        return PlaceOpeningHoursDto.builder()
                .sunday(weekdayDescriptions.has(0) ? weekdayDescriptions.get(0).asText("") : "")
                .monday(weekdayDescriptions.has(1) ? weekdayDescriptions.get(1).asText("") : "")
                .tuesday(weekdayDescriptions.has(2) ? weekdayDescriptions.get(2).asText("") : "")
                .wednesday(weekdayDescriptions.has(3) ? weekdayDescriptions.get(3).asText("") : "")
                .thursday(weekdayDescriptions.has(4) ? weekdayDescriptions.get(4).asText("") : "")
                .friday(weekdayDescriptions.has(5) ? weekdayDescriptions.get(5).asText("") : "")
                .saturday(weekdayDescriptions.has(6) ? weekdayDescriptions.get(6).asText("") : "")
                .build();
    }

}
