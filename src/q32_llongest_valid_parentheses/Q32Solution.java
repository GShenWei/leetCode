package q32_llongest_valid_parentheses;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/10/25
 */
public class Q32Solution {

    public int longestValidParentheses(String s) {
        return subFun(s);
    }

    private int subFun(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int tempMax = 0;
        for (int i = 0; i < s.length(); i++) {

            String c = s.charAt(i) + "";
            if ("(".equals(c)) {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    //如果这个栈由于之前的括号都匹配上了
                    //这时候就需要将)放进去,因为这个)在它前面是栈是空的没有(所以是不会有任何配对的
                    //这时候后续再进来)的时候,这个)所在的位置与前面的)位置相减就是一段正常的括号的长度
                    //当然,如果后面来了(,它会被正确的放到栈里面,就会覆盖这个没有的)的位置,所以不用清空栈
                    //也不能清空栈,否则之后没法比较
                    stack.push(i);
                } else {
                    tempMax = Math.max(tempMax, i - stack.peek());
                }
            }
        }
        return tempMax;
    }

    @Test
    public void tt() {
        //"(((((()))))", "()()((((((()()()",")()())","))))((()(()((()","()(()",
        List<String> strings = Arrays.asList(")(()(()(((())(((((()()))((((()()(()()())())())()))()()()())(())()()(((()))))()((()))(((())()((()()())((())))(())))())((()())()()((()((())))))((()(((((()((()))(()()(())))((()))()))())");
        for (String string : strings) {
            System.out.println(longestValidParentheses(string));
        }
    }
}
