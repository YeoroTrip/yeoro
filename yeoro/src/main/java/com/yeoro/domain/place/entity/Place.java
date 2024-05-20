package com.yeoro.domain.place.entity;

import com.yeoro.domain.place.model.dto.PlaceDto;
import com.yeoro.domain.placeDetail.entity.PlaceDetail;
import com.yeoro.entityDefault.BaseTime;

import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Place extends BaseTime{

    @NonNull
    private Long id;
    private String googleId;
    private PlaceDetail placeDetail;	// 1 : 1

    @Builder
    public Place(Long id, String googleId, PlaceDetail placeDetail) {
        this.id = id;
        this.googleId = googleId;
        this.placeDetail = placeDetail;
    }

    protected Place() {}

    public PlaceDto toDto() {
        return PlaceDto.builder()
                .googleId(this.googleId)
                .placeDetailDto(this.placeDetail.toDto())
                .build();
    }
}