package q0200_0299.q215_kth_largest_element_in_an_array;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/5
 */
public class Q215Solution {

    int result;

    public int findKthLargest(int[] nums, int k) {
        ks(nums, 0, nums.length - 1, k);
        return result;
    }

    private void ks(int[] nums, int left, int right, int k) {
        assert (nums.length > 0);
        if (left > right) {
            return;
        }
        int p = nums[left];
        int i = left, j = right;
        while (i != j) {
            while (nums[j] >= p && i < j) {
                j--;
            }
            while (nums[i] <= p && i < j) {
                i++;
            }

            if (i < j) {
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
        }
        nums[left] = nums[i];
        nums[i] = p;
        System.out.println(k);
        System.out.println(i + "--" + nums[i]);

        if (right - i + 1 == k) {
            result = nums[i];
            return;
        }
        if (right - i >= k) {
            ks(nums, i + 1, right, k);

        } else {
            k = k - (right - i + 1);
            ks(nums, left, i - 1, k);
        }
    }


    @Test
    public void x() {
        int[] nums = {1};
        findKthLargest(nums, 1);
        System.out.println(result);
        System.out.println(Arrays.toString(nums));
    }
}
