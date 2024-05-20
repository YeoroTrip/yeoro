package com.yeoro.domain.placeOpeningHours.model.dto;

import com.yeoro.domain.placeOpeningHours.entity.PlaceOpeningHours;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PlaceOpeningHoursDto {

    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;

    @Builder
    public PlaceOpeningHoursDto(String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    protected PlaceOpeningHoursDto(){}

    public PlaceOpeningHours toEntity() {
        return PlaceOpeningHours.builder()
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
