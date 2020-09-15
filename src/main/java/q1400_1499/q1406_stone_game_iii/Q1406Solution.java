package q1400_1499.q1406_stone_game_iii;

import org.junit.Test;

public class Q1406Solution {
    public String stoneGameIII(int[] stoneValue) {
        int len = stoneValue.length;
        int[] dp = new int[len + 1];
        int sum = 0;
        for (int i = len - 1; i >= 0; i--) {
            sum += stoneValue[i];
            dp[i] = Integer.MIN_VALUE;
            for (int j = i; j <= i + 2 && j < len; j++) {
                dp[i] = Math.max(dp[i], sum - dp[j + 1]);
            }
        }
        if (sum - dp[0] == dp[0]) {
            return "Tie";
        }
        return dp[0] > sum - dp[0] ? "Alice" : "Bob";
    }

    @Test
    public void xx(){
        int[] g = {1,2,3,6};
        System.out.println(stoneGameIII(g));
    }
}
