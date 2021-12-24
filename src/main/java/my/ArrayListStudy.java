package my;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Tuple2;
import static io.vavr.Predicates.is;

public class ArrayListStudy {

    @Test
    public void gg() {
        List<Integer> arr = new ArrayList<>(8);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(6);
        Integer[] ins = new Integer[10];
        Arrays.fill(ins, 99);
        Integer[] integers = arr.toArray(ins);
        System.out.println(Arrays.toString(ins));
        System.out.println(Arrays.toString(integers));
    }

    @Test
    public void xx() {
        List<Integer> arr = new ArrayList<>(8);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(6);
        arr.remove(Integer.valueOf(2));
        for (Integer integer : arr) {
            System.out.println(integer);
        }
        var now = LocalDateTime.now();
        now = now.minusHours(1);
        System.out.println(Duration.between(now, LocalDateTime.now()).toMillis());
    }

    @Test
    public void yy() {
        LocalDateTime now = LocalDateTime.now();
        int k = 3;
        for (int i = 0; i < 3; i++) {
            String xx = Match(k).of(
                    Case($(1), "1"),
                    Case($(2), "2"),
                    Case($(3), "3")
            );
        }
        System.out.println(Duration.between(now, LocalDateTime.now()).toMillis());

        now = LocalDateTime.now();
        for (int i = 0; i <= 3; i++) {
            String xx = null;
            switch (i) {
                case 1 -> xx = "1";
                case 2 -> xx = "2";
                case 3 -> xx = "3";
                default -> {
                }
            }
            System.out.println(xx);
        }
        System.out.println(Duration.between(now, LocalDateTime.now()).toMillis());
    }

    @Test
    public void rerere() {
        Tuple2<String, Integer> tup = Tuple.of("hello", 1);
        // 模式匹配
        Integer hello = Match(tup).of(
                Case($Tuple2($(is("hello")), $(is(1))), 1),
                Case($Tuple2($(), $()), 2)
        );
        System.out.println(hello);
    }

    @Test
    public void curried() {
        var sum = (Function2<Integer, Integer, Integer>) Integer::sum;
        var add2 = sum.curried().apply(2);
        Assert.assertEquals(6, add2.apply(4).intValue());
    }

    @Test
    public void xxx() {
        Gson gson = new Gson();
        String ss = "[{\"name\": \"张三\", \"idCards\": \"450217457812578945\", \"workUnits\": \"长沙市市长\", \"appellation\": \"d6986f964b794111bb897799db22c521\", \"politicalStatus\": \"d117644b302440e7a73af43fd40d3771\"}, {\"name\": \"李翠花\", \"idCards\": \"457894512478541025\", \"workUnits\": \"长沙市省委书记\", \"appellation\": \"0c868b0a270d4c3aae8be3f0531f1d7c\", \"politicalStatus\": \"81083b530c744f2988ab14de7fa381a7\"}, {\"name\": \"李二牛\", \"idCards\": \"457894257845124782\", \"workUnits\": \"教师\", \"appellation\": \"669a25b362474eccb95ea06f396cff16\", \"politicalStatus\": \"d117644b302440e7a73af43fd40d3771\"}]";

        List<Map<String, Object>> mapList = gson.fromJson(ss,
                new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        for (Map<String, Object> x : mapList) {
            System.out.println(x.get("name"));
        }
    }
}
