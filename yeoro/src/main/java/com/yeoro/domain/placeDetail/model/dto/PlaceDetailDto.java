package com.yeoro.domain.placeDetail.model.dto;

import com.yeoro.domain.placeDetail.entity.PlaceDetail;
import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import com.yeoro.domain.placeOpeningHours.model.dto.PlaceOpeningHoursDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PlaceDetailDto {

    private String name;
    private String fullAddress;
    private double latitude;
    private double longitude;
    private String category;
    private String phoneNumber;
    private Double rating;
    private String photo;
    private PlaceOpeningHoursDto placeOpeningHoursDto;

    @Builder
    public PlaceDetailDto(String name, String fullAddress, double latitude, double longitude,
                          String category, String phoneNumber, Double rating, String photo, PlaceOpeningHoursDto placeOpeningHoursDto) {
        super();
        this.name = name;
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.photo = photo;
        this.placeOpeningHoursDto = placeOpeningHoursDto;
    }

    protected PlaceDetailDto() {}

    public PlaceDetail toEntity() {
        return PlaceDetail.builder()
                .name(this.name)
                .fullAddress(this.fullAddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .category(this.category)
                .phoneNumber(this.phoneNumber)
                .rating(this.rating)
                .photo(this.photo)
                .placeOpeningHours(this.placeOpeningHoursDto.toEntity())
                .build();
    }
}
