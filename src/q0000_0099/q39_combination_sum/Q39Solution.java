package q0000_0099.q39_combination_sum;

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
public class Q39Solution {
    private List<List<Integer>> res = new ArrayList<>();

    private int[] candidates;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        this.candidates = candidates;
        find(0, target, new ArrayList<>());
        return res;
    }

    private void find(int start, int target, List<Integer> already) {
        if (target == 0) {
            res.add(new ArrayList<>(already));
            return;
        }
        Integer pre = null;
        for (int i = start; i < candidates.length; i++) {
            Integer num = candidates[i];
            if (num.equals(pre)) {
                continue;
            }
            if (target - num < 0) {
                break;
            } else {
                already.add(num);
                find(i, target - num, already);
                already.remove(already.size() - 1);
            }
            pre = num;
        }
    }

    @Test
    public void xx() {
        int[] nums = {2, 2, 5, 3, 6, 7};
        int target = 7;
        List<List<Integer>> lists = combinationSum(nums, target);
        System.out.println(lists);

    }
}
