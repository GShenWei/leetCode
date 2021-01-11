package q0000_0099.q14_longest_common_prefix;

import org.junit.Test;

/**
 * @author chenwei
 * @date 2019/12/16
 */
public class Q14Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        char cr = '0';
        for (int i = 0; ; i++) {
            boolean ok = true;
            if (strs.length == 0) {
                break;
            }
            for (String str : strs) {
                if (i >= str.length()) {
                    ok = false;
                    break;
                }
                char c = str.charAt(i);
                if (cr == '0') {
                    cr = c;
                } else {
                    if (cr != c) {
                        ok = false;
                        break;
                    }
                }
            }
            if (!ok) {
                break;
            }
            sb.append(cr);
            cr = '0';
        }
        return sb.toString();
    }

    @Test
    public void gg() {
        String s = longestCommonPrefix(new String[]{"flower", "", "flight"});
        System.out.println(s);
    }
}
