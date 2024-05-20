package com.yeoro.domain.place.model.service;

import com.yeoro.domain.place.model.dto.PlaceDto;

import java.util.List;

public interface PlaceService {

    public boolean savePlace(PlaceDto placeDto);
    public PlaceDto findPlaceByGoogleId(String googleId);
    public List<PlaceDto> findPlaceAllByGoogleIds(String textQuery, double latitude, double longitude);
    public List<String> getMissingGoogleIds(List<String> googleIds);

}
