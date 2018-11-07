package q76_minimum_window_substring;

import org.junit.Test;

import java.util.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/5
 */
public class Q76Solution {
    public String minWindow(String s, String t) {
        int left = -1;
        int right = s.length() + 1;
        //每个字符出现的位置
        Map<Character, Integer> mapOrigin = new HashMap<>();
        Map<Character, Integer> mapChg = new HashMap<>();
        //先将每个t中的char都设置好初始列表
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer integer = mapOrigin.computeIfAbsent(c, key -> 0);
            mapOrigin.put(c, ++integer);

            Integer integer2 = mapChg.computeIfAbsent(c, key -> 0);
            mapChg.put(c, ++integer2);
        }
        LinkedList<Integer> validCharIndex = new LinkedList<>();
        //现在有几个在t中的字符也在这个滑动窗口里面
        int v = 0;
        for (int l = 0, r = -1; l < s.length(); ) {
            if (r < s.length() - 1 && v < t.length()) {
                char c = s.charAt(++r);
                Integer charCount = mapChg.get(c);
                if (charCount != null) {
                    charCount--;
                    mapChg.put(c, charCount);
                    if (charCount >= 0 && charCount < mapOrigin.get(c)) {
                        v++;
                    }
                    validCharIndex.add(r);
                }
            } else if (validCharIndex.size() <= 1) {
                break;
            } else {
                //去除现在有效的第一个字符
                Integer first = validCharIndex.getFirst();
                char c = s.charAt(first);
                Integer charCount = mapChg.get(c);
                mapChg.put(c, ++charCount);
                if (charCount > 0) {
                    v--;
                }
                validCharIndex.removeFirst();
                l = validCharIndex.getFirst();
            }
            if (v == t.length()) {
                int thisLeft = validCharIndex.getFirst();
                int thisRight = validCharIndex.getLast();
                if (thisRight - thisLeft < right - left) {
                    left = thisLeft;
                    right = thisRight;
                }
            }
        }
        if (left == -1 || right == s.length() + 1) {
            return "";
        } else {
            return s.substring(left, right + 1);
        }

    }

    @Test
    public void xx() {
        String s = minWindow("cabwefgewcwaefgcf", "cae");
        System.out.println(s);
    }
}
