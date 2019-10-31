package q0100_0199.q120_triangle;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/26
 */
public class Q120Solution {

    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[] memo = new int[size];
        for (int layer = size - 1; layer >= 0; layer--) {
            for (int i = 0; i <= layer; i++) {
                if (layer == size - 1) {
                    memo[i] = triangle.get(layer).get(i);
                } else {
                    memo[i] = Math.min(memo[i], memo[i + 1]) + triangle.get(layer).get(i);
                }
            }
        }
        return memo[0];
    }


    @Test
    public void xx() {
        List<List<Integer>> x = new ArrayList<>();
        x.add(Arrays.asList(2));
        x.add(Arrays.asList(3, 4));
        x.add(Arrays.asList(6, 5, 7));
        x.add(Arrays.asList(4, 1, 8, 3));
        System.out.println(minimumTotal(x));
    }
}
