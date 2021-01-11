package q0900_0999.q992_subarrays_with_k_different_integers;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @date 2019-09-16
 */
public class Q992Solution {
    public int subarraysWithKDistinct(int[] a, int k) {
        //记录a中各个数字出现的次数,因为题中有
        //1 <= A.length <= 20000
        //1 <= A[i] <= A.length
        //1 <= K <= A.length
        int[] g = new int[a.length + 1];
        int diffCount = 0;
        int res = 0;
        //每当左边的指针向右移动一位的时候,如果减去这个数,列表还是有那么多不同的数字的时候,其实就产生了一个新的串
        int p = 0;
        for (int l = 0, r = 0; r < a.length; r++) {
            if (g[a[r]] == 0) {
                diffCount++;
            }
            g[a[r]]++;

            //这里只用减去一次,因为
            if (diffCount > k) {
                g[a[l]]--;
                l++;
                diffCount--;
                p = 0;
            }
            //这一步保证了最左边的只会有一个在滑动窗口里面
            while (g[a[l]] > 1) {
                g[a[l]]--;
                l++;
                p++;
            }
            if (diffCount == k) {
                //因为减去了左边的一个所产生的新串,以及由于加上了右边的所产生的新串
                res += p + 1;
            }
        }
        return res;
    }

    @Test
    public void go() {
        int[] a = {1, 2, 1, 2, 3};
        int k = 2;
        int i = subarraysWithKDistinct(a, k);
        System.out.println(i);
    }
}
