package q0200_0299.q209_minimum_size_subarray_sum;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/5
 */
public class Q209Solution {
    public int minSubArrayLen(int s, int[] nums) {
        int re = nums.length + 1;
        int sum = 0;
        for (int l = 0, r = -1; l < nums.length - 1; ) {
            if (r < nums.length - 1 && sum < s) {
                sum = sum + nums[++r];
            } else {
                sum = sum - nums[l++];
            }
            if (sum >= s) {
                re = Math.min(re, r - l + 1);
            }
        }
        if (re == nums.length + 1) {
            return 0;
        }
        return re;
    }

    @Test
    public void x() {
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int i = minSubArrayLen(4, nums);
        System.out.println(i);
    }
}
