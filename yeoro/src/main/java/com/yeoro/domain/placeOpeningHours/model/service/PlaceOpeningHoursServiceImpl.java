package com.yeoro.domain.placeOpeningHours.model.service;

import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import com.yeoro.domain.placeOpeningHours.model.dto.PlaceOpeningHoursDto;
import com.yeoro.domain.placeOpeningHours.model.mapper.PlaceOpeningHoursMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceOpeningHoursServiceImpl implements PlaceOpeningHoursService {

    private final PlaceOpeningHoursMapper placeOpeningHoursMapper;

    public PlaceOpeningHoursServiceImpl(PlaceOpeningHoursMapper placeOpeningHoursMapper){
        this.placeOpeningHoursMapper = placeOpeningHoursMapper;
    }

    @Override
    public Optional<PlaceOpeningHours> save(PlaceOpeningHoursDto placeOpeningHoursDto) {
        PlaceOpeningHours placeOpeningHours = PlaceOpeningHours.builder()
                .sunday(placeOpeningHoursDto.getSunday())
                .monday(placeOpeningHoursDto.getMonday())
                .tuesday(placeOpeningHoursDto.getTuesday())
                .wednesday(placeOpeningHoursDto.getWednesday())
                .thursday(placeOpeningHoursDto.getThursday())
                .friday(placeOpeningHoursDto.getFriday())
                .saturday(placeOpeningHoursDto.getSaturday())
                .build();

        int result;
        try {
            result = placeOpeningHoursMapper.insertPlaceOpeningHours(placeOpeningHours);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return result == 1 ? Optional.of(placeOpeningHours) : Optional.empty();
    }
}
