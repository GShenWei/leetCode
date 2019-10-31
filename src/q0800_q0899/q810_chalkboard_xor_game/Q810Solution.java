package q0800_q0899.q810_chalkboard_xor_game;

import org.junit.Test;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-09-20
 */
public class Q810Solution {
    public boolean xorGame(int[] nums) {
        int a = 0;
        for (int num : nums) {
            a = a ^ num;
        }
        //(nums.length & 1) == 0 偶数
        return a == 0 || (nums.length & 1) == 0;
    }

    @Test
    public void go() {
        int[] nums = {1, 2, 3};
        System.out.println(xorGame(nums));
    }

    @Test
    public void gg() {
        System.out.println(1 ^ 2);
        System.out.println(1 ^ 2);
    }
}
