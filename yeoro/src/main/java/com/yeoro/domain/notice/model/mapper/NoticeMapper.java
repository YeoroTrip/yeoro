package com.yeoro.domain.notice.model.mapper;

import com.yeoro.domain.notice.model.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeDto> getNotices();
    NoticeDto getNotice(Long id);
    Integer addNotice(NoticeDto noticeDto);
    Integer setNotice(NoticeDto noticeDto);
    Integer deleteNotice(Long id);
}
