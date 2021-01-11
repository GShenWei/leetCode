package q0800_0899.q816_ambiguous_coordinates;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @date 2019-08-19
 */
public class Q816Solution {
    public List<String> ambiguousCoordinates(String S) {
        S = S.substring(1, S.length() - 1);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < S.length() - 1; i++) {
            char c = S.charAt(i);
            String a = S.substring(0, i + 1);
            String b = S.substring(i + 1);
            if ((isAllZero(a)) || isAllZero(b)) {
                continue;
            }
            List<String> numsA = getNums(a);
            List<String> numsB = getNums(b);
            for (String sa : numsA) {
                for (String sb : numsB) {
                    res.add("(" + sa + ", " + sb + ")");
                }
            }
        }
        return res;
    }

    private boolean isAllZero(String s) {
        if (s.length() == 1) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private List<String> getNums(String s) {
        List<String> res = new ArrayList<>();
        if (s.length() == 1) {
            res.add(s);
            return res;
        }
        if (s.startsWith("0") && s.endsWith("0")) {
            return res;
        }
        if (s.startsWith("0")) {
            res.add("0." + s.substring(1));
            return res;
        }
        res.add(s);
        if (s.endsWith("0")) {
            return res;
        }
        for (int i = 0; i < s.length() - 1; i++) {
            res.add(s.substring(0, i + 1) + "." + s.substring(i + 1));
        }
        return res;
    }

    @Test
    public void yy() {
        List<String> nums = getNums("1");
        String join = String.join(",", nums);
        System.out.println(join);
    }

    @Test
    public void xx() {
        List<String> strings = ambiguousCoordinates("(0010)");
        String join = String.join(", ", strings);
        System.out.println(join);
    }
}
