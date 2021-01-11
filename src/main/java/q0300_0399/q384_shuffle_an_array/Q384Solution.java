package q0300_0399.q384_shuffle_an_array;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class Q384Solution {
    private int[] nums;
    private Random r = new Random();

    public Q384Solution() {

    }
    /*public Q384Solution(int[] nums) {
        this.nums = nums;
    }*/

    /**
     * Resets the array to its original configuration and return it.
     */
    public int[] reset() {
        return this.nums;
    }

    /**
     * Returns a random shuffling of the array.
     */
    public int[] shuffle() {
        int[] copy = new int[nums.length];

        int ind = 0;
        for (int num : nums) {
            while ((ind = r.nextInt(nums.length)) < 0 || copy[ind] != 0) {
            }
            copy[ind] = num;
        }

        return copy;
    }


    @Test
    public void xx() {
        int[] arr = {1, 2, 3};
        Q384Solution x = new Q384Solution();
        x.nums = arr;
        int[] shuffle = x.shuffle();
        System.out.println(Arrays.toString(shuffle));
        System.out.println(Arrays.toString(x.reset()));

    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
