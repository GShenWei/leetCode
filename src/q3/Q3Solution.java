package q3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q3Solution
 * @description
 * @date 2017/5/24
 */
public class Q3Solution {
    public int lengthOfLongestSubstring(String s) {
        int result = 0;
        int l = 0;
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            List<Integer> charNums = map.get(c);
            if (charNums == null) {
                charNums = new ArrayList<>();
                charNums.add(i);
                map.put(c, charNums);
            } else {
                int i1 = charNums.get(charNums.size() - 1) + 1;
                l = l< i1 ? i1 :l;
                charNums.add(i);
            }
            if (result < i - l + 1) {
                result = i - l + 1;
            }
        }
        return result;
    }

    @Test
    public void oo() {
        int y = lengthOfLongestSubstring("cccccccvbbghnnefwaragerhtfjbaefwddyuiujyfhtdgrfedghyjkkiujyhtgbbb");
        System.out.println(y);
    }
}
