package com.yeoro.domain.notice.model.service;

import com.yeoro.domain.notice.model.dto.NoticeDto;

import java.util.List;

public interface NoticeService {
    int createNotice(NoticeDto noticeDto);

    NoticeDto findNotice(Long id);

    List<NoticeDto> findAllNotice();

    int modifyNotice(NoticeDto noticeDto);

    int removeNotice(Long id);
}
