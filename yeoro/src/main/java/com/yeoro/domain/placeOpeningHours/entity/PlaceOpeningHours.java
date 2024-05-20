package com.yeoro.domain.placeOpeningHours.entity;

import com.yeoro.domain.placeOpeningHours.model.dto.PlaceOpeningHoursDto;
import lombok.*;

@Getter
@Setter
public class PlaceOpeningHours {

    private Long id;
    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;

    @Builder
    public PlaceOpeningHours(Long id, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday) {
        super();
        this.id = id;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    protected PlaceOpeningHours(){}

    public PlaceOpeningHoursDto toDto(){
        return PlaceOpeningHoursDto.builder()
                .sunday(this.sunday)
                .monday(this.monday)
                .tuesday(this.tuesday)
                .wednesday(this.wednesday)
                .thursday(this.thursday)
                .friday(this.friday)
                .saturday(this.saturday)
                .build();
    }
}
