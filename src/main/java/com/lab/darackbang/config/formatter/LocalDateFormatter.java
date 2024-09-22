package com.lab.darackbang.config.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    // 사용할 포맷 패턴을 상수로 정의
    private static final String PATTERN = "yyyy-MM-dd";

    // LocalDateTime을 문자열로 변환 (출력)
    @Override
    public String print(LocalDate object, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return object.format(formatter);
    }

    // 문자열을 LocalDateTime으로 변환 (파싱)
    @Override
    public LocalDate parse(String text, Locale locale) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDate.parse(text, formatter);
    }
}
