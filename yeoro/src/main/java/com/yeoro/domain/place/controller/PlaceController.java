package com.yeoro.domain.place.controller;


import com.yeoro.domain.place.model.service.PlaceServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yeoro.domain.place.model.dto.PlaceDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/places")
@Tag(name = "여행지 컨트롤러", description = "여행지 관련 API")
public class PlaceController {

    private final PlaceServiceImpl placeService;
    public PlaceController(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }

    @Operation(summary = "여행지 조회", description = "검색어와 좌표를 받아 여행지 반환")
    @GetMapping
    public ResponseEntity<?> getPlaces(@Parameter(name = "textQuery")String textQuery,
                                       @Parameter(name = "latitude")double latitude,
                                       @Parameter(name = "longitude")double longitude){
        log.info("[getPlaces] placeDto - {}", textQuery);

        List<PlaceDto> placeDtoList = placeService.findPlaceAllByGoogleIds(textQuery,latitude,longitude);

        return ResponseEntity.ok(placeDtoList);
    }

//    @Operation(summary = "여행지 등록", description = "여행지 정보를 받아 데이터베이스에 등록.")
//    @PostMapping
//    public ResponseEntity<?> placeRegister(@RequestBody(description = "등록할 여행정보.", required = true, content = @Content(schema = @Schema(implementation = PlaceDto.class)))
//                                           @org.springframework.web.bind.annotation.RequestBody PlaceDto placeDto) {
//        log.info("[placeRegister] placeDto - {}", placeDto);
//
//        boolean isOk = placeService.savePlace(placeDto);
//
//        if(isOk) return ResponseEntity.ok().build();
//        else return ResponseEntity.badRequest().build();
//    }

    @Operation(summary = "여행지 조회 - 구글 아이디 사용", description = "구글 ID를 받아 데이터베이스에서 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaceDetail(@Parameter(name = "id") @PathVariable(name = "id") String googleId){
        PlaceDto placeDto = placeService.findPlaceByGoogleId(googleId);
        log.info("[getPlaceDetail] placeDto - {}", placeDto);

        if(placeDto != null) return ResponseEntity.ok(placeDto);
        else return ResponseEntity.notFound().build();
    }


}

