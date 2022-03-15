package my;

import org.junit.Test;

import java.text.Format;
import java.text.MessageFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/29
 */
public class LocalDateTest {
    @Test
    public void xx() {
        LocalDate ld = LocalDate.now();
        ld = ld.plusMonths(-1);
        ld = ld.withDayOfMonth(1);
        ZonedDateTime zonedDateTime = ld.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date from = Date.from(instant);
        System.out.println(ld);
    }

    @Test
    public void yy() {
        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        LocalDateTime ld = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String format = ld.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.println(format);
    }

    @Test
    public void ji() {
        DateTimeFormatter dm = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate aha = LocalDate.now();
        WeekFields fd = WeekFields.of(DayOfWeek.MONDAY, 7);
        LocalDate tm = aha.with(fd.weekOfYear(), 1);
        System.out.println(tm);
    }

    @Test
    public void mk() {
        DateTimeFormatter dm = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate d = LocalDate.parse("20220313", dm);
        var tb = Period.between(LocalDate.now(), d).getDays();
        System.out.println(tb);
        String.format("%02d",d.getMonth().getValue());
        System.out.printf("%02d",d.getMonth().getValue());
    }
}
