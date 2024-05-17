package com.yeoro.domain.notice.model.service;

import com.yeoro.domain.notice.model.dto.NoticeDto;
import com.yeoro.domain.notice.model.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{

    private NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper){
        super();
        this.noticeMapper = noticeMapper;
    }

    @Override
    public int createNotice(NoticeDto noticeDto) {
        try {
            int result = noticeMapper.addNotice(noticeDto);
            if (result == 1) {
                // 공지사항 등록에 성공한 경우
                return 1;
            } else {
                // 공지사항 등록에 실패한 경우
                return 0;
            }
        } catch (Exception e) {
            // 공지사항 등록 중 예외가 발생한 경우
            e.printStackTrace(); // 예외 내용을 출력
            return 0; // 실패로 처리
        }
    }


    @Override
    public NoticeDto findNotice(Long id) {
        try {
            int updateResult = noticeMapper.increaseHits(id);
            if(updateResult == 0)   // 업데이트 실패
                return null;

            NoticeDto noticeDto = noticeMapper.getNotice(id);
            if(noticeDto == null)   // 조회 실패
                return null;

            return noticeDto;

        }catch (Exception e){
            // 공지사항 조회 중 예외가 발생한 경우
            e.printStackTrace(); // 예외 내용을 출력
            return null; // 실패로 처리
        }
    }

    @Override
    public List<NoticeDto> findAllNotice() {
        try{
            List<NoticeDto> notices = noticeMapper.getNotices();
            if(notices.isEmpty()) return null;

            return notices;
        } catch (Exception e){
            // 공지사항 조회 중 예외가 발생한 경우
            e.printStackTrace(); // 예외 내용을 출력
            return null; // 실패로 처리
        }
    }

    @Override
    public int modifyNotice(NoticeDto noticeDto) {
        try {
            int result = noticeMapper.setNotice(noticeDto);
            if(result == 0) return 0;

            return 1;
        }catch (Exception e){
            // 공지사항 수정 중 예외가 발생한 경우
            e.printStackTrace(); // 예외 내용을 출력
            return 0; // 실패로 처리
        }
    }

    @Override
    public int removeNotice(Long id) {
        try {
            int result = noticeMapper.deleteNotice(id);
            if(result == 0) return 0;

            return 1;
        }catch (Exception e){
            // 공지사항 삭제 중 예외가 발생한 경우
            e.printStackTrace(); // 예외 내용을 출력
            return 0; // 실패로 처리
        }
    }
}
