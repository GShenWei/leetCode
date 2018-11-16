package q15_3sum;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/7
 */
public class Q15Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int k = 0; k < nums.length - 2; ) {
            int n1 = nums[k];
            if(n1>0) {
                break;
            }
            for (int l = k + 1, r = nums.length - 1; l < r; ) {
                int n2 = nums[l];
                int n3 = nums[r];
                int thisR = n1 + n2 + n3;
                if (thisR == 0) {
                    if (res.size() > 0) {
                        List<Integer> lastResult = res.get(res.size() - 1);
                        int preN1 = lastResult.get(0);
                        int preN2 = lastResult.get(1);
                        int preN3 = lastResult.get(2);
                        if (preN1 == n1 && preN2 == n2 && preN3 == n3) {
                            l++;
                            continue;
                        }
                    }
                    List<Integer> rk = new ArrayList<>();
                    rk.add(n1);
                    rk.add(n2);
                    rk.add(n3);
                    res.add(rk);
                    l++;
                } else if (thisR > 0) {
                    r--;
                } else {
                    l++;
                }
            }
            while (k < nums.length - 2 && nums[k] == nums[k + 1]) {
                k++;
            }
            k++;
        }
        return res;
    }

    @Test
    public void x() {
        int[][] numss = {
                //{-4, -1, -1, 0, 1, 2},
                //{1, -1, -1, 0},
                //{-1, -1, 2},
                //{-1, -4, -2, -1, -1, 2, 3, 4},
                //{-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6},
                {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0},
                //{0, 0, 0, 0, 0},
        };
        int[] a = {1,};
        System.out.println(a.length);
        for (int[] nums : numss) {
            List<List<Integer>> lists = threeSum(nums);
            System.out.println(lists);
        }
    }
}
