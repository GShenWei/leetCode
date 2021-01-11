package my;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        LocalDateTime now = LocalDateTime.now();
        List<Integer> arr = new ArrayList<>(8);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(6);
        arr.remove(new Integer(2));
        for (Integer integer : arr) {
            System.out.println(integer);
        }
        System.out.println(Duration.between(now, LocalDateTime.now()).toMillis());
    }

    @Test
    public void yy() {
        LocalDateTime now = LocalDateTime.now();
        int k = 3;
        for (int i = 0; i < 1; i++) {
            String xx = Match(k).of(
                    Case($(1), "1"),
                    Case($(2), "2"),
                    Case($(3), "3")
            );
        }
        System.out.println(Duration.between(now, LocalDateTime.now()).toMillis());

        now = LocalDateTime.now();
        for (int i = 0; i < 1; i++) {
            String xx;
            switch (k) {
                case 1:
                    xx = "1";
                    break;
                case 2:
                    xx = "1";
                    break;
                case 3:
                    xx = "1";
                    break;
                default:
                    break;
            }
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

}
