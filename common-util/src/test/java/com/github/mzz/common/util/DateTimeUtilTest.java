package com.github.mzz.common.util;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author mengzz
 **/
public class DateTimeUtilTest {

    @Test
    public void parseDateTime() {
        String text = "2020-05-25 11:00:00";
        StopWatch started = StopWatch.createStarted();
        for (int i = 0; i < 20000; i++) {
            Long time = DateTimeUtil.toEpochMilli(DateTimeUtil.parseDateTime(text));
        }
        System.out.println(started.getTime());
    }

    @Test
    public void fastParseDate() {
        String text = "2020-05-25 11:00:00";
        StopWatch started = StopWatch.createStarted();
        for (int i = 0; i < 20000; i++) {
            Date date = DateTimeUtil.fastParseDate(text);
            Long time = date.getTime();
        }
        System.out.println(started.getTime());
    }

    @Test
    public void parseDate() {
        String text = "2020-05-25";
        Long time = DateTimeUtil.toEpochMilli(DateTimeUtil.parseDate(text).atStartOfDay());
        System.out.println(time);
    }
}
