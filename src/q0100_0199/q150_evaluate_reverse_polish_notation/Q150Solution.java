package q0100_0199.q150_evaluate_reverse_polish_notation;

import org.junit.Test;

import java.util.Stack;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/12
 */
public class Q150Solution {
    public int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        for (String str : tokens) {
            if ("+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str)) {

                String str1 = stack.pop();
                String str2 = stack.pop();

                Integer n1 = new Integer(str1);
                Integer n2 = new Integer(str2);

                int res;
                if ("+".equals(str)) {
                    res = n2 + n1;
                } else if ("-".equals(str)) {
                    res = n2 - n1;
                } else if ("*".equals(str)) {
                    res = n2 * n1;
                } else {
                    res = n2 / n1;
                }
                stack.push(res + "");
            } else {
                stack.push(str);
            }
        }
        return Integer.parseInt(stack.pop());
    }

    @Test
    public void xx() {
        String[] x = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        int i = evalRPN(x);
        System.out.println(i);
    }
}
