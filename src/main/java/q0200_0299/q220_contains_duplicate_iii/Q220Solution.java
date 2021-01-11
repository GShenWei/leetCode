package q0200_0299.q220_contains_duplicate_iii;

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
        //floor(E e) 方法返回在这个集合中小于或者等于给定元素的最大元素，如果不存在这样的元素,返回null.
        //ceiling(E e) 方法返回在这个集合中大于或者等于给定元素的最小元素，如果不存在这样的元素,返回null.
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            long thisNum = (long) num;
            Long floor = set.floor(thisNum + t);
            if (floor != null && floor >= thisNum - t) {
                return true;
            } else {
                set.add(thisNum);
                //如果set中的个数超过了范围，就删除掉最前面那个（就算因为set的特性，那个数已经被后来同样数的代替了，但是它还是这个数）
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
