package q0000_0099.q62_unique_paths;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/28
 */
public class Q62Solution {
    public int uniquePaths(int m, int n) {
        int pn = m + n - 2;
        int pr = n - 1;
        if (pn == 0) {
            return 1;
        }
        int maxX = Math.max(pr, pn - pr);
        int minX = Math.min(pr, pn - pr);
        //这道题就是求排列，能在m-1+n-1步中放进去多少个right(或者down)
        long x1 = 1;
        for (int i = pn; i > maxX; i--) {
            x1 = x1 * i;
        }
        long x2 = 1;
        for (int i = 1; i <= minX; i++) {
            x2 = x2 * i;
        }
        return (int) (x1 / x2);
    }

    @Test
    public void xx() {
        System.out.println(uniquePaths(36, 7));
        System.out.println(uniquePaths(2, 3));
        System.out.println(uniquePaths(100, 20));
    }

}
