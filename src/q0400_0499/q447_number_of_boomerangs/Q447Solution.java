package q0400_0499.q447_number_of_boomerangs;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/7
 */
public class Q447Solution {
    public int numberOfBoomerangs(int[][] points) {
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            int x = points[i][0];
            int y = points[i][1];
            Map<Integer, Integer> lengthMap = new HashMap<>();
            for (int k = 0; k < points.length; k++) {
                if (i == k) {
                    continue;
                }
                int xK = points[k][0];
                int yK = points[k][1];
                int len = (xK - x) * (xK - x) + (yK - y) * (yK - y);
                Integer orDefault = lengthMap.getOrDefault(len, 0) + 1;
                lengthMap.put(len, orDefault);
            }
            for (int count : lengthMap.values()) {
                if (count > 1) {
                    res += count * (count - 1);
                }
            }
        }
        return res;
    }

    @Test
    public void xx() {
        int[][] p = {{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        System.out.println(numberOfBoomerangs(p));
    }
}
