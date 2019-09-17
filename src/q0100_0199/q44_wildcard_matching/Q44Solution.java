package q0100_0199.q44_wildcard_matching;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @date 2019-08-16
 */
public class Q44Solution {
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] f = new boolean[n + 1][m + 1];
        f[0][0] = true;
        for (int i = 1; i <= m; i++) {
            f[0][i] = f[0][i - 1] && '*' == p.charAt(i - 1);
        }
        for (int i = 1; i <= n; i++) {
            boolean allFalse = true;
            char sc = s.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char pc = p.charAt(j - 1);
                if (sc == pc || pc == '?') {
                    f[i][j] = f[i - 1][j - 1];
                }
                if (pc == '*') {
                    f[i][j] = f[i][j - 1] || f[i - 1][j];
                }
                allFalse = allFalse && !f[i][j];
            }
            if (allFalse) {
                return false;
            }
        }
        return f[n][m];
    }

    @Test
    public void xx() {
        String s = "aa";
        String p = "*";
        assertTrue(isMatch(s, p));
    }

}
