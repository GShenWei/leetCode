package q0100_0199.q198_house_robber;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/28
 */
public class Q198SolutionJ {
    public int rob(int[] nums) {
        int l = nums.length;
        if (l == 0) {
            return 0;
        }
        int[] peak = new int[l];
        if (l == 1) {
            return nums[0];
        } else if (l == 2) {
            return Math.max(nums[0], nums[1]);
        } else {
            peak[0] = nums[0];
            peak[1] = Math.max(nums[0], nums[1]);
            for (int i = 2; i < l; i++) {
                peak[i] = Math.max(peak[i - 2] + nums[i], peak[i - 1]);
            }
            return peak[l - 1];
        }
    }

    @Test
    public void xx() {
        int[] n = {1, 2, 3, 1};
        System.out.println(rob(n));
    }
}
