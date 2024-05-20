package com.yeoro.domain.place.model.dto;

import com.yeoro.domain.place.entity.Place;
import com.yeoro.domain.placeDetail.model.dto.PlaceDetailDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class PlaceDto {

    private String googleId;
    private PlaceDetailDto placeDetailDto;

    @Builder
    public PlaceDto(String googleId, PlaceDetailDto placeDetailDto) {
        super();
        this.googleId = googleId;
        this.placeDetailDto = placeDetailDto;
    }

    protected PlaceDto() {}

    public Place toEntity() {
        return Place.builder()
                .googleId(this.googleId)
                .placeDetail(this.placeDetailDto.toEntity())
                .build();
    }
}
