package q6;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q6Solution
 * @description
 * @date 2017/5/26
 */
public class Q6Solution {
    public String convert(String s, int numRows) {
        String result = "";
        Map<Integer, String> map = new LinkedHashMap<>();
        boolean re = false;
        int j = 1;
        for (int i = 0; i < s.length(); i++) {
            String s1 = map.get(j);
            if (s1 == null) {
                s1 = s.charAt(i) + "";
                map.put(j, s1);

            } else {
                map.put(j, s1 + s.charAt(i) + "");
            }
            if (re) {
                j--;
            } else {
                j++;
            }
            if (j == numRows) {
                re = true;
            }
            if (j == 1) {
                re = false;
            }
        }
        for (Map.Entry<Integer, String> p : map.entrySet()) {
            result = result + p.getValue();
        }
        return result;
    }

    @Test
    public void oo() {
        String ss = convert("PAYPALISHIRING", 3);
        boolean ok = ss.equals("PAHNAPLSIIGYIR");
        System.out.println(ss);
    }
}
