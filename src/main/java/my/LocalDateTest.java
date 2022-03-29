package my;

import org.junit.Test;

import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        String.format("%02d", d.getMonth().getValue());
        System.out.printf("%02d", d.getMonth().getValue());
    }

    @Test
    public void klk() {
        var n = LocalDate.of(2015, 1, 1);
        var fd = WeekFields.of(DayOfWeek.MONDAY, 4);
        System.out.println(n.format(DateTimeFormatter.ISO_WEEK_DATE));
        System.out.println(n.get(fd.weekOfYear()));
    }

    @Test
    public void sim() throws IllegalAccessException {
        Stu data = new Stu();
        data.value1 = "x";
        data.value2 = "x";
        data.value3 = "y";
        data.value4 = "y";
        Set<String> s = new HashSet<>();
        Field[] fs = Stu.class.getDeclaredFields();
        for (Field f : fs) {
            String name = f.getName();
            System.out.println(name);
            if (name.startsWith("value")) {
                f.setAccessible(true);
                s.add(f.get(data) + "");
            }
        }
        for (String ss : s) {
            System.out.println(ss);
        }
    }

}

class Stu {
    String value1;
    String value2;
    String value3;
    String value4;
}
