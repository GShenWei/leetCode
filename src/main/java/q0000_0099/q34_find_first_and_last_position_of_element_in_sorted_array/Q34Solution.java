package q0000_0099.q34_find_first_and_last_position_of_element_in_sorted_array;

import org.junit.Test;

import java.util.Arrays;

public class Q34Solution {

    public int[] searchRange(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int index = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (target == nums[mid]) {
                index = mid;
                break;
            } else if (target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (index < 0) {
            return new int[]{-1, -1};
        }
        int left = index, right = index;
        boolean lB = false, rB = false;
        for (int i = 1; ; i++) {
            if (!lB && index - i >= 0) {
                if (nums[index - i] == nums[index]) {
                    left = index - i;
                }
            } else {
                lB = true;
            }
            if (!rB && index + i <= nums.length - 1) {
                if (nums[index + i] == nums[index]) {
                    right = index + i;
                }
            } else {
                rB = true;
            }
            if (lB && rB) {
                break;
            }
        }
        return new int[]{left, right};
    }

    @Test
    public void gg() {
        int[] x = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(Arrays.toString(x));
    }

    @Test
    public void gg2() {
        int[] x = searchRange(new int[]{1,2,3,3,3,3,4,5,9}, 3);
        System.out.println(Arrays.toString(x));
    }

    @Test
    public void xxxxx() {
        System.out.println(2%2);
    }
}
