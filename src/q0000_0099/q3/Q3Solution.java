package q0000_0099.q3;

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
        //所存储的是某个字符在原始字符串中的最后一个字符位置
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
                //获取在插入现在这个字符之前目前这个字符所在的位置
                int i1 = charNums.get(charNums.size() - 1);
                //获取它们最大的原因是,可能之前的l已经考虑过其它的字符会有重复的
                l = Math.max(l, i1);
                charNums.add(i);
            }
            result = Math.max(result, i - l );
        }
        return result;
    }

    @Test
    public void oo() {
        int y = lengthOfLongestSubstring("cccccccvbbghnnefwaragerhtfjbaefwddyuiujyfhtdgrfedghyjkkiujyhtgbbb");
        System.out.println(y);
    }
}
