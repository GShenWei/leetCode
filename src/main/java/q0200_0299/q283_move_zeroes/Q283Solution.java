package q0200_0299.q283_move_zeroes;

import org.junit.Test;

import java.util.Arrays;

public class Q283Solution {
    public void moveZeroes(int[] nums) {
        int[] res = new int[nums.length];
        int i = 0;
        for (int num : nums) {
            if (num != 0) {
                res[i++] = num;
            }
        }
        System.arraycopy(res, 0, nums, 0, res.length);
    }

    @Test
    public void xx() {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes3(nums);
        System.out.println(Arrays.toString(nums));
    }

    public void moveZeroes2(int[] nums) {
        int p = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                nums[p++] = nums[i];
            }
        }
        for (int i = p; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    public void moveZeroes3(int[] nums) {
        int p = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                if (i != p) {
                    int t = nums[i];
                    nums[i] = nums[p];
                    nums[p] = t;
                }
                p++;
            }
        }
    }
}
