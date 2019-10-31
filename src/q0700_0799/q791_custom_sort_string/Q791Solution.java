package q0700_0799.q791_custom_sort_string;

import java.util.HashMap;
import java.util.Map;

public class Q791Solution {
    public String customSortString(String S, String T) {
        String s = S;
        String t = T;
        Map<Character, Integer> indexMap = new HashMap<>(26);
        for (int i = 0; i < s.length(); i++) {
            indexMap.put(s.charAt(i), i);
        }
        StringBuilder[] resSbs = new StringBuilder[indexMap.size()];
        StringBuilder notExSb = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer index = indexMap.get(c);
            if (index == null) {
                notExSb.append(c);
            } else {
                StringBuilder re = resSbs[index];
                if (re == null) {
                    re = new StringBuilder();
                    resSbs[index] = re;
                }
                re.append(c);
            }
        }
        StringBuilder resb = new StringBuilder();
        for (StringBuilder re : resSbs) {
            if (re != null) {
                resb.append(re);
            }
        }
        resb.append(notExSb);
        return resb.toString();
    }
}
