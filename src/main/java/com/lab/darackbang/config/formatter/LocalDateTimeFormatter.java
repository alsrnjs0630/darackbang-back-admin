package com.lab.darackbang.config.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    // 사용할 포맷 패턴을 상수로 정의
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    // LocalDateTime을 문자열로 변환 (출력)
    @Override
    public String print(LocalDateTime object, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return object.format(formatter);
    }

    // 문자열을 LocalDateTime으로 변환 (파싱)
    @Override
    public LocalDateTime parse(String text, Locale locale) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDateTime.parse(text, formatter);
    }
}
