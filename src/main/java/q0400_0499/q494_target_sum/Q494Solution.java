package q0400_0499.q494_target_sum;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-09-25
 */
public class Q494Solution {
    private int[][] memo;

    public int findTargetSumWays(int[] nums, int s) {
        memo = new int[nums.length][2001];
        for (int[] row : memo) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        int res = calculate(nums, 0, 1000, s);
        /*Map<String, Integer> mm = new LinkedHashMap<>();
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[i].length; j++) {
                if (memo[i][j] != Integer.MIN_VALUE) {
                    mm.put(i + "," + (j-1000), memo[i][j]);
                }
            }
        }*/
        return res;
    }

    private int calculate(int[] nums, int i, int sum, int s) {
        if (i == nums.length) {
            if (sum - 1000 == s) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (memo[i][sum] != Integer.MIN_VALUE) {
                return memo[i][sum];
            }
            int add = calculate(nums, i + 1, sum + nums[i], s);
            int subtract = calculate(nums, i + 1, sum - nums[i], s);
            memo[i][sum] = add + subtract;
            return memo[i][sum];
        }
    }

    @Test
    public void go() {
        int[] ss = {1, 1, 1, 1, 1};
        int s = 3;
        System.out.println(findTargetSumWays(ss, s));
    }
}
