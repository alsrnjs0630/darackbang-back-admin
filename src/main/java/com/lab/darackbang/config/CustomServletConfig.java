package com.lab.darackbang.config;

import com.lab.darackbang.config.formatter.LocalDateFormatter;
import com.lab.darackbang.config.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CustomServletConfig는 WebMvcConfigurer를 구현하여
 * Spring MVC의 포매터(Formatter)를 등록하는 설정 클래스입니다.
 */
@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    /**
     * addFormatters 메서드는 FormatterRegistry에
     * LocalDateFormatter와 LocalDateTimeFormatter를 추가하여
     * LocalDate와 LocalDateTime 데이터를 각각 원하는 형식으로 처리할 수 있게 합니다.
     *
     * @param registry FormatterRegistry에 포매터를 등록합니다.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // LocalDate 타입을 "yyyy-MM-dd" 형식으로 변환 및 파싱
        registry.addFormatter(new LocalDateFormatter());

        // LocalDateTime 타입을 "yyyy-MM-dd HH:mm:ss" 형식으로 변환 및 파싱
        registry.addFormatter(new LocalDateTimeFormatter());
    }

}
