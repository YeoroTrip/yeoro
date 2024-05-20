package com.yeoro.domain.placeOpeningHours.model.mapper;

import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceOpeningHoursMapper {

    // 여행지 영업시간 등록
    int insertPlaceOpeningHours(PlaceOpeningHours placeOpeningHours);
}
