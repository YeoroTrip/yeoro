package com.yeoro.domain.placeDetail.model.service;


import com.yeoro.domain.placeDetail.model.dto.PlaceDetailDto;
import com.yeoro.domain.placeDetail.entity.PlaceDetail;
import com.yeoro.domain.placeDetail.model.mapper.PlaceDetailMapper;
import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import com.yeoro.domain.placeOpeningHours.model.service.PlaceOpeningHoursService;
import com.yeoro.domain.webClient.model.service.PlacePhotoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceDetailServiceImpl implements PlaceDetailService {

    private final PlacePhotoService placePhotoService;
    private final PlaceDetailMapper placeDetailMapper;
    private final PlaceOpeningHoursService placeOpeningHoursService;

    public PlaceDetailServiceImpl(PlaceDetailMapper placeDetailMapper,
                                  PlaceOpeningHoursService placeOpeningHoursService,
                                  PlacePhotoService placePhotoService){
        this.placeDetailMapper = placeDetailMapper;
        this.placeOpeningHoursService = placeOpeningHoursService;
        this.placePhotoService = placePhotoService;
    }

    public Optional<PlaceDetail> save(PlaceDetailDto placeDetailDto) {
        try {
            Optional<PlaceOpeningHours> placeOpeningHoursOpt = placeOpeningHoursService.save(placeDetailDto.getPlaceOpeningHoursDto());

            // 장소 사진을 가져와서 저장된 URL을 얻음
            String photoUrl = placePhotoService.saveAndGetPhotoUrl(placeDetailDto.getPhoto())
                    .block(); // 비동기 작업이므로 block()으로 결과를 기다림


            if (placeOpeningHoursOpt.isPresent()) {
                PlaceOpeningHours placeOpeningHours = placeOpeningHoursOpt.get();

                PlaceDetail placeDetail = PlaceDetail.builder()
                        .name(placeDetailDto.getName())
                        .fullAddress(placeDetailDto.getFullAddress())
                        .latitude(placeDetailDto.getLatitude())
                        .longitude(placeDetailDto.getLongitude())
                        .category(placeDetailDto.getCategory())
                        .phoneNumber(placeDetailDto.getPhoneNumber())
                        .rating(placeDetailDto.getRating())
                        .photo(photoUrl) // google 서버에 저장된 이미지 URL로 변경
                        .placeOpeningHours(placeOpeningHours)
                        .build();

                if (placeDetailMapper.insertPlaceDetail(placeDetail) == 1) {
                    return Optional.of(placeDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
