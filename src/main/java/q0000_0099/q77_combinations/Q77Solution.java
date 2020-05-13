package q0000_0099.q77_combinations;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/22
 */
public class Q77Solution {
    private List<List<Integer>> res = new ArrayList<>();
    private boolean[] used;
    private int n;
    private int k;

    public List<List<Integer>> combine(int n, int k) {
        if (n == 0 || k <= 0) {
            return res;
        }
        used = new boolean[n + 1];
        this.n = n;
        this.k = k;
        find(0, 1, new ArrayList<>());
        return res;
    }

    private void find(int index, int min, List<Integer> already) {
        if (index == k) {
            res.add(new ArrayList<>(already));
            return;
        }
        //这里主要是限制本次循环的最大值，这样可以让后续的递归能有足够的空位放下后续的数字
        int maxNum = n - (k - already.size()) + 1;
        for (int i = min; i <= maxNum; i++) {
            if (!used[i]) {
                already.add(i);
                find(index + 1, i + 1, already);
                already.remove(already.size() - 1);
                used[i] = false;
            }
        }
    }

    @Test
    public void xx() {
        List<List<Integer>> combine = combine(4, 2);
        System.out.println(combine);
    }
}
