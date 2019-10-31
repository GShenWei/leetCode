package q0000_0099.q15_3sum_closest;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/7
 */
public class Q16Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        Integer min = null;
        for (int k = 0; k < nums.length - 2; k++) {
            int n1 = nums[k];
            if (k != 0 && n1 > target) {
                break;
            }
            int l = k + 1;
            int r = nums.length - 1;
            while (l < r) {
                int n2 = nums[l];
                int n3 = nums[r];
                if (min == null) {
                    min = n1 + n2 + n3;
                }
                int thisMin = n1 + n2 + n3;
                if (thisMin == target) {
                    return thisMin;
                }
                if (thisMin < target) {
                    l++;
                } else {
                    r--;
                }
                if (Math.abs(min - target) > Math.abs(thisMin - target)) {
                    min = thisMin;
                }

            }
        }
        return min == null ? 0 : min;
    }

    @Test
    public void xx() {
        int[] nums = {1, 1, -1, -1, 3};
        int target = -1;
        int i = threeSumClosest(nums, target);
        System.out.println(i);
    }
}
