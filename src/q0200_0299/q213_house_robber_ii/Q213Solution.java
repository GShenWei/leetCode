package q0200_0299.q213_house_robber_ii;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/28
 */
public class Q213Solution {
    public int rob(int[] nums) {
        int len = nums.length;
        int[][] memo = new int[2][len];
        if (len == 0) {
            return 0;
        } else if (len == 1) {
            return nums[0];
        } else if (len == 2) {
            return Math.max(nums[0], nums[1]);
        } else if (len == 3) {
            return Math.max(Math.max(nums[0], nums[1]), nums[2]);
        } else {
            for (int start = 0; start <= 1; start++) {
                memo[start][0] = nums[start];
                memo[start][1] = Math.max(nums[start], nums[start + 1]);
                //不能到最后一个，因为这样会使收尾相连
                for (int i = 2; i < len - 1; i++) {
                    int k;
                    if (i + start >= len) {
                        k = i + start - len;
                    } else {
                        k = i + start;
                    }
                    memo[start][i] = Math.max(memo[start][i - 2] + nums[k], memo[start][i - 1]);
                }
            }
            return Math.max(memo[0][len - 2], memo[1][len - 2]);
        }
    }

    @Test
    public void xx() {
        int[] nums = {1, 7, 9, 2};
        int rob = rob(nums);
        System.out.println(rob);
    }
}
