package com.yeoro.domain.placeOpeningHours.model.service;

import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import com.yeoro.domain.placeOpeningHours.model.dto.PlaceOpeningHoursDto;

import java.util.Optional;

public interface PlaceOpeningHoursService {
    public Optional<PlaceOpeningHours> save(PlaceOpeningHoursDto placeOpeningHoursDto);
}
