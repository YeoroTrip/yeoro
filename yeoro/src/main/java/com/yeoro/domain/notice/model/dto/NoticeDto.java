package com.yeoro.domain.notice.model.dto;

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
    private LocalDateTime createdAt;
    //@Schema(description = "수정 일시")
    private LocalDateTime updatedAt;

}
