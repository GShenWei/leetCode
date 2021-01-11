package q0100_0199.q131_palindrome_partitioning;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/21
 */
public class Q131Solution {
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> partition(String s) {
        bloom(s, new ArrayList<>());
        return res;
    }

    private void bloom(String s, List<String> alreadyStrs) {
        if (s.length() == 0) {
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            String thisPart = s.substring(0, i + 1);
            String nextPart = s.substring(i + 1);
            if (isPalindrome(thisPart)) {
                alreadyStrs.add(thisPart);
                if (nextPart.length() == 0) {
                    res.add(new ArrayList<>(alreadyStrs));
                } else {
                    bloom(nextPart, alreadyStrs);
                }
                alreadyStrs.remove(alreadyStrs.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return false;
        } else {
            if (s.length() == 1) {
                return true;
            }
            for (int i = 0; i < s.length() / 2; i++) {
                if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Test
    public void xx() {
        String x = "";
        System.out.println(partition(x));
    }
}
