package q1100_1199.q1106_parsing_a_boolean_expression;


import org.junit.Test;

import java.util.Stack;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-09-17
 */
public class Q1106Solution {
    public boolean parseBoolExpr(String expression) {
        if (expression.length() == 1) {
            return parse(expression);
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            stack.push(c);
            if (c == ')') {
                StringBuilder sb = new StringBuilder();
                while (stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                sb.append(stack.pop());
                sb.append(stack.pop());
                if (parse(sb.toString())) {
                    stack.push('t');
                } else {
                    stack.push('f');
                }
            }
        }
        return parse(stack.pop() + "");
    }

    private boolean parse(String k) {
        if ("t".equalsIgnoreCase(k)) {
            return true;
        } else if ("f".equalsIgnoreCase(k)) {
            return false;
        } else {
            String g = k.substring(k.length() - 1);
            String s = k.substring(1, k.length() - 2);
            String[] split = s.split(",");
            boolean res = false;
            switch (g) {
                case "!":
                    res = "f".equalsIgnoreCase(split[0]);
                    break;
                case "|":
                    res = false;
                    for (String s1 : split) {
                        if ("t".equalsIgnoreCase(s1)) {
                            res = true;
                            break;
                        }
                    }
                    break;
                case "&":
                    res = true;
                    for (String s1 : split) {
                        if ("f".equalsIgnoreCase(s1)) {
                            res = false;
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
            return res;
        }
    }

    @Test
    public void go() {
        boolean k = parseBoolExpr("|(&(t,f,t),!(t))");
        System.out.println(k);
    }
}
