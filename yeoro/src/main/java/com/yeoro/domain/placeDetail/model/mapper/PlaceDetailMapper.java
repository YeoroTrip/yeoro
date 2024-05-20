package com.yeoro.domain.placeDetail.model.mapper;

import com.yeoro.domain.placeDetail.entity.PlaceDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceDetailMapper {

    // 여행지 상세정보 등록
    int insertPlaceDetail(PlaceDetail placeDetail);
}
