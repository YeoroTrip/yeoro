package com.yeoro.domain.place.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yeoro.domain.place.model.dto.PlaceDto;
import com.yeoro.domain.place.entity.Place;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaceMapper {

    // 여행지 등록
    int insertPlace(Place place);

    // 여행지 조회 - 구글 아이디 기반
    PlaceDto selectPlaceByGoogleId(String googleId);

    // 여행지 조회 - 구글 아이디 리스트 기반
    List<PlaceDto> selectAllPlaceByGoogleIds(@Param("list") List<String> googleIds);

    // 여행지 수정
    int updatePlace(Place place);

    // 여행지 삭제
    int deletePlace(Long googleId);
}
