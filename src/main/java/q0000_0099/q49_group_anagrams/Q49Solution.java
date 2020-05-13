package q0000_0099.q49_group_anagrams;

import org.junit.Test;

import java.util.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/7
 */
public class Q49Solution {
    /*public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);
            List<String> orDefault = map.computeIfAbsent(sortedStr, key -> {
                ArrayList<String> list = new ArrayList<>();
                res.add(list);
                return list;
            });
            orDefault.add(s);
        }
        return res;
    }*/

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<EachCharCountArr, List<String>> map = new HashMap<>();
        for (String s : strs) {
            EachCharCountArr ecca = new EachCharCountArr(s);
            List<String> orDefault = map.computeIfAbsent(ecca, key -> {
                ArrayList<String> list = new ArrayList<>();
                res.add(list);
                return list;
            });
            orDefault.add(s);
        }
        return res;
    }

    class EachCharCountArr {
        int[] arr = new int[26];

        EachCharCountArr(String str) {
            for (char c : str.toCharArray()) {
                int i = c - 'a';
                arr[i]++;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            EachCharCountArr that = (EachCharCountArr) o;

            return Arrays.equals(arr, that.arr);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(arr);
        }
    }

    @Test
    public void xx() {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> lists = groupAnagrams(strs);
        System.out.println(lists);
    }

}
