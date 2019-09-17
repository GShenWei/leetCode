package q0300_0399.q301_remove_invalid_parentheses;

import org.junit.Test;

import java.util.*;

public class Q301Solution {

    public List<String> removeInvalidParentheses(String s) {
        LinkedList<String> queue = new LinkedList<>();
        List<String> res = new ArrayList<>();
        //用于存储裁剪后的元素，防止重复元素加入队列
        HashSet<String> set = new HashSet<>();
        //只要成功找到一个,那么另外的删除最少可以得到的必定在这个队列里面
        boolean isFound = false;
        queue.push(s);
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (isOk(curr)) {
                res.add(curr);
                isFound = true;
            }
            if (isFound) {
                continue;
            }
            for (int i = 0; i < curr.length(); i++) {
                if (curr.charAt(i) == '(' || curr.charAt(i) == ')') {
                    String str;
                    if (i == curr.length() - 1) {
                        str = curr.substring(0, curr.length() - 1);
                    } else {
                        str = curr.substring(0, i) + curr.substring(i + 1);
                    }
                    if (set.add(str)) {
                        queue.offer(str);
                    }
                }
            }
        }
        return res;
    }

    private boolean isOk(String s) {
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                k++;
            }
            if (c == ')') {
                k--;
            }
            if (k < 0) {
                return false;
            }
        }
        return k == 0;
    }

    public List<String> removeInvalidParentheses2(String s) {
        List<String> res = new ArrayList<>();
        char[] check = new char[]{'(', ')'};
        dfs(s, res, check, 0, 0);
        return res;
    }

    public void dfs(String s, List<String> res, char[] check, int last_i, int last_j) {
        int count = 0;
        int i = last_i;
        while (i < s.length() && count >= 0) {

            if (s.charAt(i) == check[0]) {
                count++;
            }
            if (s.charAt(i) == check[1]) {
                count--;
            }
            i++;
        }

        if (count >= 0) {
            // no extra ')' is detected. We now have to detect extra '(' by reversing the string.
            String reversed = new StringBuffer(s).reverse().toString();
            if (check[0] == '(') {
                dfs(reversed, res, new char[]{')', '('}, 0, 0);
            } else {
                res.add(reversed);
            }

        } else {
            // extra ')' is detected and we have to do something
            // 'i-1' is the index of abnormal ')' which makes count<0
            i -= 1;
            for (int j = last_j; j <= i; j++) {
                if (s.charAt(j) == check[1] && (j == last_j || s.charAt(j - 1) != check[1])) {
                    dfs(s.substring(0, j) + s.substring(j + 1, s.length()), res, check, i, j);
                }
            }
        }
    }

    @Test
    public void xx() {
        List<String> res = removeInvalidParentheses2("))())(()(((()t)");
        System.out.println(Arrays.toString(res.toArray()));
    }
}
