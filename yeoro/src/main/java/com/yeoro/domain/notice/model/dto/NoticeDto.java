package com.yeoro.domain.notice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDto {
    //@Schema(description = "아이디")
    private Long id;

    //@Schema(description = "제목")
    private String title;

    //@Schema(description = "내용)
    private String context;

    //@Schema(description = "조회수")
    private Long hits;

    //@Schema(description = "중요 여부")
    private boolean isImportant;

    //@Schema(description = "생성 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    //@Schema(description = "수정 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

}
