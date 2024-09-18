package com.lab.darackbang.repository;

import com.lab.darackbang.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    void insert() { // 이벤트 등록 테스트
        log.info("이벤트 등록 테스트 ----------------------------");

        for (int i = 0; i < 10; i++) {
            Event event = Event.builder().title("추석맞이 이벤트" + i)
                    .contents("즐거운 한가위를 맞아 전품목 " + i + "% 할인")
                    .fileName("이미지없음")
                    .eventState("02")
                    .startDate(LocalDate.of(2024,9,16))
                    .endDate(LocalDate.of(2024,9,20))
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();

            eventRepository.save(event);
        }
    }

    @Test
    void eventRead() { // 이벤트 상세정보 테스트
        Event event = eventRepository.findById(7L).orElseThrow();

        log.info("이벤트 제목 : " + event.getTitle());
        log.info("내용 : " + event.getContents());

    }

    @Test
    void eventUpdate() { // 이벤트 수정(삭제)
        Event event = eventRepository.findById(7L).orElseThrow();

        event.setTitle("추석 이벤트 6 [종료]");
        event.setContents("본 이벤트는 종료되었습니다.");
        event.setUpdatedDate(LocalDate.now());
        event.setEventState("01");
        eventRepository.save(event);
    }
}
