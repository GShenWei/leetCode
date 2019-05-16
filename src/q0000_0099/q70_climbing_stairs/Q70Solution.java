package q0000_0099.q70_climbing_stairs;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/26
 */
public class Q70Solution {
    /*private int[] memo;

    public int climbStairs(int n) {
        memo = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = -1;
        }
        return c(n);
    }

    public int c(int n) {
        if (n == 0 || n == 1) {
            memo[n] = 1;
        } else {
            if (memo[n] == -1) {
                memo[n] = c(n - 1) + c(n - 2);
            }
        }
        return memo[n];
    }*/


    public int climbStairs(int n) {
        int[] memo = new int[n + 1];
        memo[0] = 1;
        memo[1] = 1;
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[n];
    }

    @Test
    public void xx() {
        System.out.println(climbStairs(44));
    }
}
