package my;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/26
 */
public class Fibonacci {
    @Test
    public void xx() {
        int n = 100;
        long[] memo = new long[n + 1];
        memo[0] = 1;
        memo[1] = 1;
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        System.out.println(memo[n]);
    }

    @Test
    public void gg(){
        Map<Integer,String> m = new HashMap<>();
        m.put(1,"haha");
        m.put(2,"haha");
        m.put(3,"haha");
        List<Long> li = new ArrayList<>();
        li.add(1L);
        li.add(2L);
        List<Integer> collect = li.stream().map(Long::intValue).filter(m::containsKey).collect(Collectors.toList());
        System.out.println(collect.size());
    }
}
