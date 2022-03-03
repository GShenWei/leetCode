package my.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class SimpleDate {
    @Test
    public void td() throws ParseException {
        String start = "2019-01";
        String end = "2020-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        LocalDate startDate = sdf.parse(start).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = sdf.parse(end).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(1);
        for (LocalDate i = startDate; i.isBefore(endDate); i = i.plusMonths(1)) {
            String x = String.format("%02d", i.getMonth().getValue());
            System.out.println(x);
        }

    }
}
