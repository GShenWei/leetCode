package q220_contains_duplicate_iii;

import org.junit.Test;

import java.util.TreeSet;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/8
 */
public class Q220Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k, int t) {
        if (t < 0) {
            return false;
        }
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            long thisNum = (long) num;
            Long floor = set.floor(thisNum + t);
            if (floor != null && floor >= thisNum - t) {
                return true;
            } else {
                set.add(thisNum);
                if (set.size() > k) {
                    set.remove((long) nums[i - k]);
                }
            }
        }
        return false;
    }

    @Test
    public void xx() {
        int[] nums = {-1, -1};
        int k = 1;
        int t = -1;
        System.out.println(containsNearbyDuplicate(nums, k, t));
    }
}
