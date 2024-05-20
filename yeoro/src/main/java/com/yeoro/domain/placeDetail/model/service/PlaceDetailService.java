package com.yeoro.domain.placeDetail.model.service;

import com.yeoro.domain.placeDetail.entity.PlaceDetail;
import com.yeoro.domain.placeDetail.model.dto.PlaceDetailDto;

import java.util.Optional;

public interface PlaceDetailService {
    public Optional<PlaceDetail> save(PlaceDetailDto placeDetailDto);
}
