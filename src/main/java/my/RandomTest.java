package my;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-10-09
 */
public class RandomTest {
    @Test
    public void xx() {
        Random random = new Random();
        //random.setSeed(10000L);
        System.out.println(random.nextInt());
    }

    public Function<String, String> x = (a) -> "a" + a;

    @Test
    public void gio() {
        String aaa = x.apply("aaa");
        Function<String, String> b = (a) -> aaa + a;
    }

    public Function<Integer, String> func1 = String::valueOf;
    public Function<Integer, String> func2 = Integer::toBinaryString;

    public void t(int a) {
        Function<Integer, String> f;
        if (a > 0) {
            f = func1;
        } else {
            f = func2;
        }
        System.out.println(f.apply(a));
    }

    public Supplier<Integer> getData(int step) {
        AtomicInteger a = new AtomicInteger(0);
        return () -> a.incrementAndGet() + step;
    }
    @Test
    public void gg() {
        Supplier<Integer> data = getData(1);
        for (int i = 0; i < 10; i++) {
            System.out.println(data.get());
        }
    }

}
