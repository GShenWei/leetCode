package my;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
}
