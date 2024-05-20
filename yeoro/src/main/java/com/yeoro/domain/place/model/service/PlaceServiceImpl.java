package com.yeoro.domain.place.model.service;

import com.yeoro.domain.placeDetail.entity.PlaceDetail;
import com.yeoro.domain.placeDetail.model.service.PlaceDetailServiceImpl;
import com.yeoro.domain.webClient.model.service.PlaceSearchService;
import org.springframework.stereotype.Service;

import com.yeoro.domain.place.model.dto.PlaceDto;
import com.yeoro.domain.place.entity.Place;
import com.yeoro.domain.place.model.mapper.PlaceMapper;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;
    private final PlaceDetailServiceImpl placeDetailService;
    private final PlaceSearchService placeSearchService;

    public PlaceServiceImpl(PlaceMapper placeMapper,
                            PlaceDetailServiceImpl placeDetailService,
                            PlaceSearchService placeSearchService) {
        this.placeMapper = placeMapper;
        this.placeDetailService = placeDetailService;
        this.placeSearchService = placeSearchService;
    }

    public boolean savePlace(PlaceDto placeDto) {
        Optional<PlaceDetail> placeDetail = placeDetailService.save(placeDto.getPlaceDetailDto());

        if(!placeDetail.isPresent()) return false;

        Place place = Place.builder()
                .googleId(placeDto.getGoogleId())
                .placeDetail(placeDetail.get())
                .build();

        if(placeMapper.insertPlace(place) ==1) return true;
        else return false;
    }

    public PlaceDto findPlaceByGoogleId(String googleId){
        return placeMapper.selectPlaceByGoogleId(googleId);
    }

    public List<PlaceDto> findPlaceAllByGoogleIds(String textQuery, double latitude, double longitude){
        // 1. Google API 호출
        List<PlaceDto> googlePlaces = placeSearchService.searchPlaces(textQuery, latitude, longitude).block();

        // 2. Google API에서 가져온 googleId 목록을 추출
        List<String> googleIds = googlePlaces.stream()
                .map(PlaceDto::getGoogleId)
                .collect(Collectors.toList());

        // 3. 데이터베이스에 없는 googleId 목록을 필터링하여 저장
        List<String> missingGoogleIds = getMissingGoogleIds(googleIds);

        // 4. 데이터베이스에 없는 googleId에 해당하는 PlaceDto를 저장
        for (PlaceDto newPlace : googlePlaces) {
            if (missingGoogleIds.contains(newPlace.getGoogleId())) {
                savePlace(newPlace);
            }
        }

        // 5. 데이터베이스에 존재하는 모든 장소 목록을 반환
        return placeMapper.selectAllPlaceByGoogleIds(googleIds);
    }

    public List<String> getMissingGoogleIds(List<String> googleIds) {
        // 1. 데이터베이스에서 일치하는 googleId를 찾음
        List<PlaceDto> existingPlaces = placeMapper.selectAllPlaceByGoogleIds(googleIds);

        // 2. 데이터베이스에 존재하는 googleId 목록을 Set으로 변환
        Set<String> existingGoogleIds = existingPlaces.stream()
                .map(PlaceDto::getGoogleId)
                .collect(Collectors.toSet());

        // 3. googleIds에 있지만 데이터베이스에 없는 googleId 목록을 필터링하여 반환
        return googleIds.stream()
                .filter(googleId -> !existingGoogleIds.contains(googleId))
                .collect(Collectors.toList());
    }
}
