package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
public class NoticeRepositoryTest {
    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void noticeInsert() {
        log.info("공지사항 등록 테스트 ---------------------");
        for (int i = 0; i < 15; i++) {
            Notice notice = Notice.builder().title("공지사항 테스트" + i)
                    .contents(i + "번째 공지사항 등록")
                    .build();

            noticeRepository.save(notice);
        }
    }

    @Test
    public void noticeRead() {
        log.info("공지사항 상세정보 테스트 --------------------");
        Notice notice = noticeRepository.findById(6L).orElseThrow();

        log.info("공지사항 제목: " + notice.getTitle());
        log.info("공지사항 내용: " + notice.getContents());
        log.info("등록일: " + notice.getCreatedDate());
        log.info("수정욀: " + notice.getUpdatedDate());
    }

    @Test
    public void noticeUpdate() { // 공지사항은 is_delete가 없음. 삭제 불가능?
        log.info("공지사항 수정 테스트 ----------------------");
        Notice notice = noticeRepository.findById(6L).orElseThrow();

        notice.setTitle("추석연휴 배송안내");
        notice.setContents("추석연휴에는 배송되지않습니다.");

        noticeRepository.save(notice);
    }
}
