package com.github.mzz.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The type Date time util.
 *
 * @author mengzz
 */
public class DateTimeUtil {
    private static final String DATE_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_DEFAULT_PATTERN = "yyyy-MM-dd";
    private static ZoneId zone = ZoneId.systemDefault();

    /**
     * Parse date.
     *
     * @param text the text
     * @return the local date
     */
    public static LocalDate parseDate(String text) {
        return parseDate(text, DATE_TIME_DEFAULT_PATTERN);
    }

    /**
     * Parse date.
     *
     * @param text    the text
     * @param pattern the pattern
     * @return the local date
     */
    public static LocalDate parseDate(String text, String pattern) {
        try {
            return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse date time.
     *
     * @param text the text
     * @return the local date time
     */
    public static LocalDateTime parseDateTime(String text) {
        return parseDateTime(text, DATE_DEFAULT_PATTERN);
    }

    /**
     * Parse date time.
     *
     * @param text    the text
     * @param pattern the pattern
     * @return the local date time
     */
    public static LocalDateTime parseDateTime(String text, String pattern) {
        try {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * To epoch milli long.
     *
     * @param dateTime the date time
     * @return the long
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.atZone(zone).toInstant().toEpochMilli();
    }

    /**
     * Fast parse date.
     *
     * @param text the text
     * @return the date
     */
    public static Date fastParseDate(String text) {
        return fastParseDate(text, DATE_DEFAULT_PATTERN);
    }

    /**
     * Fast parse date.
     *
     * @param text    the text
     * @param pattern the pattern
     * @return the date
     */
    public static Date fastParseDate(String text, String pattern) {
        try {
            return FastDateFormat.getInstance(pattern).parse(text);
        } catch (Exception e) {
            return null;
        }
    }

}
