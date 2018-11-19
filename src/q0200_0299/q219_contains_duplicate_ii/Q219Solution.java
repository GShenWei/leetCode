package q0200_0299.q219_contains_duplicate_ii;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/8
 */
public class Q219Solution {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (set.contains(num)) {
                return true;
            } else {
                set.add(num);
                if (set.size() > k) {
                    set.remove(nums[i - k]);
                }
            }
        }
        return false;
    }

    @Test
    public void xx() {
        int[] nums = {1,2,3,1,2,3};
        boolean b = containsNearbyDuplicate(nums, 2);
        System.out.println(b);
    }
}
