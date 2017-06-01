package q9;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q9Solution
 * @description
 * @date 2017/5/31
 */
public class Q9Solution {
    public boolean isPalindrome(int x) {
        String s = x+"";
        if(s.startsWith("-")){
            s=s.substring(1);
        }
        StringBuilder sb = new StringBuilder(s);
        sb = sb.reverse();
        return s.equals(sb.toString());
    }

    @Test
    public void oo(){
        boolean palindrome = isPalindrome(2147447412);
        System.out.println(palindrome);
    }
}
