package com.lab.darackbang.mapper;


import com.lab.darackbang.dto.common.PageDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Page 객체와 PageDTO 객체 간의 매핑을 담당하는 매퍼 인터페이스입니다.
 * MapStruct 라이브러리를 사용하여 구현됩니다.
 */
@Mapper(componentModel = "spring")
public interface PageMapper {

    /**
     * Page 객체를 PageDTO 객체로 변환하는 기본 매핑 메서드입니다.
     *
     * @param <E> Page에 포함된 요소의 타입
     * @param page Spring Data의 Page 객체
     * @return 변환된 PageDTO 객체
     */
    default <E,S> PageDTO<E,S> toDTO(Page<E> page, S search) {
        if (page == null) {
            return null;
        }

        PageDTO<E,S> pageDTO = new PageDTO<>();

        // 콘텐츠 매핑
        pageDTO.setContents(page.getContent());

        // 페이지 번호 매핑
        pageDTO.setPageNumbers(IntStream.rangeClosed(1, page.getTotalPages())
                .boxed()
                .collect(Collectors.toList()));

        // 이전 및 다음 페이지 여부 설정
        pageDTO.setPrev(page.hasPrevious());
        pageDTO.setNext(page.hasNext());

        // 전체 항목 수, 전체 페이지 수, 현재 페이지 설정
        pageDTO.setTotalCount((int) page.getTotalElements());
        pageDTO.setTotalPage(page.getTotalPages());
        pageDTO.setCurrentPage(page.getNumber() + 1);  // 1부터 시작하는 페이지 번호로 변환

        // 이전 및 다음 페이지 번호 설정
        pageDTO.setPrevPage(page.hasPrevious() ? page.getNumber() : null);
        pageDTO.setNextPage(page.hasNext() ? page.getNumber() + 2 : null);  // 1부터 시작하는 페이지 번호로 조정

        pageDTO.setSearch(search);

        return pageDTO;
    }
}
