package q0000_0099.q33_search_in_rotated_sorted_array;

import org.junit.Test;

/**
 * @author chenwei
 * @date 2019/12/16
 */
public class Q33Solution {
    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int index = findIndex(nums, 0, nums.length - 1);
        if (nums[index] == target) {
            return index;
        } else if (target > nums[index] && target <= nums[nums.length - 1]) {
            return search(nums, index + 1, nums.length - 1, target);
        } else {
            return search(nums, 0, index - 1, target);
        }
    }

    public int search(int[] nums, int start, int end, int target) {
        while (start <= end) {
            int mid = (end + start) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else if (target < nums[mid]) {
                end = mid - 1;
            }
        }
        return -1;
    }

    public int findIndex(int[] nums, int start, int end) {
        if (nums[start] < nums[end] || nums.length == 1) {
            return 0;
        }
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] > nums[mid + 1]) {
                return mid + 1;
            } else {
                if (nums[mid] < nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }
        return 0;
    }

    @Test
    public void haha() {
        int[] nums = {1,2,3,0};
        int target =1;
        System.out.println(search(nums, target));
    }
}
