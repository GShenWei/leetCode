package q0000_0099.q5;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q5Solution
 * @description
 * @date 2017/5/25
 */
public class Q5Solution {
    public String longestPalindrome(String s) {
        String result = "";
        String P = "芈";
        s = s.replaceAll("(.)", "$1" + P);
        s = P + s;
        int maxLen = 0;
        int maxIndex = 0;
        int[] a = new int[s.length()];
        int maxRight = 0;
        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i < maxRight) {
                a[i] = Math.min(a[2 * pos - i], maxRight - i);
            } else {
                a[i] = 1;
            }
            while (i - a[i] >= 0 && i + a[i] < s.length() && s.charAt(i - a[i]) == s.charAt(i + a[i])) {
                a[i]++;
            }
            if (i + a[i] - 1 > maxRight) {
                maxRight = i + a[i] - 1;
                pos = i;
            }
            if (maxLen < a[i]) {
                maxLen = a[i];
                maxIndex = i;
            }
        }
        result = s.substring(maxIndex - maxLen + 1, maxIndex + maxLen - 1);
        result = result.replaceAll(P, "");
        return result;
    }

    @Test
    public void oo() {
        System.out.println(longestPalindrome("aaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeeeeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkk"));
    }
}
