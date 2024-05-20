package com.yeoro.domain.placeDetail.entity;

import com.yeoro.domain.placeDetail.model.dto.PlaceDetailDto;
import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import com.yeoro.entityDefault.BaseTime;
import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDetail extends BaseTime {

    @NonNull
    private Long id;
    private String name;
    private String fullAddress;
    private double latitude;
    private double longitude;
    private String category;
    private String phoneNumber;
    private Double rating;
    private String photo;
    private PlaceOpeningHours placeOpeningHours; // 1 : 1

    @Builder
    public PlaceDetail(Long id, String name, String fullAddress, double latitude, double longitude,
                       String category, String phoneNumber, Double rating, String photo, PlaceOpeningHours placeOpeningHours) {
        super();
        this.id = id;
        this.name = name;
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.photo = photo;
        this.placeOpeningHours = placeOpeningHours;
    }

    protected PlaceDetail() {}

    public PlaceDetailDto toDto() {
        return PlaceDetailDto.builder()
                .name(this.name)
                .fullAddress(this.fullAddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .category(this.category)
                .phoneNumber(this.phoneNumber)
                .rating(this.rating)
                .photo(this.photo)
                .placeOpeningHoursDto(this.placeOpeningHours.toDto())
                .build();
    }
}
