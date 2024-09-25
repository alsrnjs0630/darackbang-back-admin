package com.lab.darackbang.dto.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

import java.io.Serializable;
import java.util.List;

/**
 * 페이지네이션 정보를 처리하는 데이터 전송 객체 (DTO)입니다.
 * 이 클래스는 제네릭을 사용하여 다양한 타입의 콘텐츠를 페이지네이션할 수 있습니다.
 *
 * @param <E> 페이지네이션할 요소의 타입
 */
@Setter
@Getter
@ToString
public class PageDTO<E> implements Serializable {

    /**
     * 현재 페이지에 표시될 콘텐츠 목록입니다.
     */
    private List<E> contents;

    /**
     * 페이지네이션에 표시할 페이지 번호 목록입니다.
     */
    private List<Integer> pageNumbers;

    /**
     * 이전 페이지가 있는지 여부를 나타냅니다.
     */
    private Boolean prev;

    /**
     * 다음 페이지가 있는지 여부를 나타냅니다.
     */
    private Boolean next;

    /**
     * 전체 항목의 총 개수입니다.
     */
    private Integer totalCount;

    /**
     * 이전 페이지의 페이지 번호입니다.
     */
    private Integer prevPage;

    /**
     * 다음 페이지의 페이지 번호입니다.
     */
    private Integer nextPage;

    /**
     * 전체 페이지 수입니다.
     */
    private Integer totalPage;

    /**
     * 현재 페이지 번호입니다.
     */
    private Integer currentPage;
}
