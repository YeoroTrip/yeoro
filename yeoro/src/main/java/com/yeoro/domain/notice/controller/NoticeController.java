package com.yeoro.domain.notice.controller;

import com.yeoro.domain.notice.model.dto.NoticeDto;
import com.yeoro.domain.notice.model.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notice")
@Slf4j
@Tag(name = "공지사항 API", description = "공지사항 관련 API")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Operation(summary = "공지사항 모두 조회", description = "모든 공지사항을 조회해 반환한다.")
    @GetMapping
    public ResponseEntity<List<NoticeDto>> getAllNotice() {
        try {
            List<NoticeDto> notices = noticeService.findAllNotice();
            if (!notices.isEmpty()) {
                return ResponseEntity.ok(notices);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("모든 공지사항 조회 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "공지사항 조회", description = "공지사항 ID를 입력받아 조회해 반환한다.")
    @Parameter(name = "id", description = "공지사항 아이디", example = "2")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDto> getNoticeById(@PathVariable Long id) {
        try {
            NoticeDto notice = noticeService.findNotice(id);
            if (notice != null) {
                return ResponseEntity.ok(notice);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            log.error("ID가 " + id + "인 공지사항 조회 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "공지사항 추가",
            description = "공지사항을 입력받아 추가한다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoticeDto.class),
                            examples = @ExampleObject(
                                    name = "공지사항 예제",
                                    summary = "공지사항 예제 입력",
                                    description = "이 예제" +
                                            "는 공지사항 추가를 위한 예제입니다.",
                                    value = "{\"title\": \"공지사항 제목\", \"context\": \"공지사항 내용\", \"important\": true}"
                            )
                    )
            )
    )
    @PostMapping
    public ResponseEntity<Void> addNotice(@Valid @RequestBody NoticeDto noticeDto) {
        try {
            int isOk = noticeService.createNotice(noticeDto);
            if (isOk == 1) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            log.error("공지사항 추가 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "공지사항 수정",
            description = "공지사항을 입력받아 수정한다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoticeDto.class),
                            examples = @ExampleObject(
                                    name = "공지사항 예제",
                                    summary = "공지사항 예제 입력",
                                    description = "이 예제는 공지사항 수정을 위한 예제입니다.",
                                    value = "{\"title\": \"공지사항 제목 수정\", \"context\": \"공지사항 내용 수정\", \"important\": true}"
                            )
                    )
            )
    )
    @Parameter(name = "id", description = "공지사항 아이디", example = "2")
    @PostMapping("/{id}")
    public ResponseEntity<Void> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeDto noticeDto) {
        try {
            noticeDto.setId(id);
            int isOk = noticeService.modifyNotice(noticeDto);
            if (isOk == 1) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            log.error("ID가 " + id + "인 공지사항 수정 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항 ID를 입력받아 삭제한다.")
    @Parameter(name = "id", description = "공지사항 아이디", example = "2")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        try {
            int isOk = noticeService.removeNotice(id);
            if (isOk == 1) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            log.error("ID가 " + id + "인 공지사항 삭제 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
