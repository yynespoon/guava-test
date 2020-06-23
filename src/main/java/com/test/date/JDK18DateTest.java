package com.test.date;

import org.junit.Test;

import java.time.*;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.Locale;

/**
 * @author lixiaoyu
 * @since 2020/6/23
 */
public class JDK18DateTest {

    @Test
    public void testLocalDate(){
        // 根据指定年份月份当月天数生成 LocalDate
        System.out.println(LocalDate.of(2020, 1, 1));
        // 时间所在年份有多少天
        System.out.println(LocalDate.now().lengthOfYear());
        // 时间所在天的起始日期
        System.out.println(LocalDate.now().atStartOfDay());
        // 获取月份
        System.out.println(LocalDate.now().getMonth());
        // 是否是闰年
        System.out.println(LocalDate.now().isLeapYear());
        // 获取当天在所在周的位置
        System.out.println(LocalDate.now().getDayOfWeek());
        // 获取当前年份
        System.out.println(LocalDate.now().getYear());
        // 当一周的开始与一个月的开始对齐的时候返回当天在所在周的第几天
        System.out.println(LocalDate.now().withYear(2020).withMonth(7).get(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
        // 返回当天在所在周的第几天
        System.out.println(LocalDate.now().withYear(2020).withMonth(7).get(ChronoField.DAY_OF_WEEK));
        // 默认格式化 yyyy-MM-dd
        System.out.println(LocalDate.parse("2020-01-01"));
    }

    @Test
    public void testLocalTime(){
        // format
        System.out.println(LocalTime.parse("10:00:00.66666"));
    }

    @Test
    public void testLocalDateTime(){
        // format
        System.out.println(LocalDateTime.parse("2020-01-01T23:59:59.99999"));
        // 格式化到天
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        // 转化为 localDate
        System.out.println(LocalDateTime.now().toLocalDate());
    }

    @Test
    public void testInstant(){
        // instant 转 date
        System.out.println(new Date(Instant.now().toEpochMilli()));
    }

    /**
     * 范围较小
     */
    @Test
    public void testDuration(){
        // 获取两个时间的区间值转化为秒数
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().plus(1L, ChronoUnit.DAYS)).get(ChronoUnit.SECONDS));
        // 当前时间加上时间偏移量
        System.out.println(LocalDateTime.now().plus(Duration.of(1L, ChronoUnit.DAYS)));
        // 减一秒
        Duration.of(1L, ChronoUnit.SECONDS).minusSeconds(1);
    }

    /**
     * 范围较大
     */
    @Test
    public void testPeriod(){
        System.out.println(Period.between(LocalDate.now(), LocalDate.now().withMonth(7)).get(ChronoUnit.YEARS));
        System.out.println(Period.between(LocalDate.now(), LocalDate.now().withMonth(7)).get(ChronoUnit.MONTHS));
        System.out.println(LocalDate.now().plus(Period.of(10, 1, 1)));
    }

    @Test
    public void testTemporalAdjuster(){
        // 10周后的星期一
        System.out.println(LocalDate.now().with(TemporalAdjusters.dayOfWeekInMonth(10, DayOfWeek.of(1))));
        // 下个工作日
        System.out.println(LocalDate.now().with(ChronoField.DAY_OF_WEEK, 5L).with(temporal -> {
            int add = 1;
            int i = temporal.get(ChronoField.DAY_OF_WEEK);
            if(i == 5){
                add = 3;
            } else if(i == 6){
                add = 2;
            }
            return temporal.plus(add, ChronoUnit.DAYS);
        }));
    }

    @Test
    public void testZone(){
        System.out.println(Chronology.ofLocale(Locale.CHINA).dateNow());
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        System.out.println(LocalDate.now().atStartOfDay(zoneId));

        System.out.println(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(8)));
    }
}
