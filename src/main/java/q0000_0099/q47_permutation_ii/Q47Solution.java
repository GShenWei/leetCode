package q0000_0099.q47_permutation_ii;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/22
 */
public class Q47Solution {
    private List<List<Integer>> res = new ArrayList<>();
    private boolean[] used;


    public List<List<Integer>> permuteUnique(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return res;
        } else {
            //这一步是将所有相同的数字放到一起，这样在放置的时候，如果这一次放的数据跟上一次放的相同，那么就可以跳过不放了
            Arrays.sort(nums);
        }
        used = new boolean[length];
        for (int i = 0; i < used.length; i++) {
            used[i] = false;
        }
        find(0, nums, new ArrayList<>());
        return res;
    }

    private void find(int index, int[] nums, List<Integer> alreadyList) {
        if (index == nums.length) {
            res.add(new ArrayList<>(alreadyList));
            return;
        }
        Integer pre = null;
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                if (pre != null && pre == nums[i]) {
                    continue;
                }
                alreadyList.add(nums[i]);
                used[i] = true;
                find(index + 1, nums, alreadyList);
                used[i] = false;
                alreadyList.remove(alreadyList.size() - 1);
                pre = nums[i];
            }
        }
    }

    @Test
    public void xx() {
        int[] nums = {3, 3, 0, 3};
        List<List<Integer>> lists = permuteUnique(nums);
        System.out.println(lists);
    }

}
