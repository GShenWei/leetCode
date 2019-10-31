package q0000_0099.q40_combination_sum_ii;

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
@SuppressWarnings("Duplicates")
public class Q40Solution {

    private List<List<Integer>> res = new ArrayList<>();

    private int[] candidates;

    private boolean[] used;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        used = new boolean[candidates.length];
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
            if (!used[i]) {
                Integer num = candidates[i];
                if (num.equals(pre)) {
                    continue;
                }
                if (target - num < 0) {
                    break;
                } else {
                    used[i] = true;
                    already.add(num);
                    find(i, target - num, already);
                    already.remove(already.size() - 1);
                    used[i] = false;
                }
                pre = num;

            }
        }
    }

    @Test
    public void xx() {
        int[] nums = {10,1,2,7,6,1,5};
        int target = 8;
        List<List<Integer>> lists = combinationSum2(nums, target);
        System.out.println(lists);

    }
}
