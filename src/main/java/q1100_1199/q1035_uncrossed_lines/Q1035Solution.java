package q1100_1199.q1035_uncrossed_lines;

import org.junit.Test;

public class Q1035Solution {
    public int maxUncrossedLines(int[] a, int[] b) {
        int[][] dp = new int[a.length + 1][b.length + 1];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[a.length][b.length];
    }

    @Test
    public void xx() {
        int[] a = {2,4,3};
        int[] b = {2,3,4};
        int i = maxUncrossedLines(a, b);
        System.out.println(i);
    }
}
