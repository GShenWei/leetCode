package q0700_0799.q736_parse_lisp_expression;

import org.junit.Test;

import java.util.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/12/28
 */
public class Q736Solution {
    //TODO
    Map<String, Integer> valueMap = new HashMap<>();


    public int evaluate(String expression) {
        String ex = expression.replaceAll("([()])", " $1 ");
        ex = ex.replaceAll(" {2}", " ");
        ex = ex.trim();
        String[] exs = ex.split(" ");
        Stack<String> stack = new Stack<>();
        Set<String> keyWords = new HashSet<>();
        keyWords.add("let");
        keyWords.add("add");
        keyWords.add("mult");

        String preOpt = null;
        for (String x : exs) {
            if (")".equals(x)) {
                List<String> vs = new ArrayList<>();
                while (true) {
                    String pop = stack.pop();
                    if (!keyWords.contains(pop)) {
                        vs.add(pop);
                    } else {
                        int cal = cal(vs, pop);
                        stack.push(cal + "");
                        break;
                    }
                }
            } else if (!"(".equals(x)) {
                //如果是普通的字符的话，就直接入栈
                stack.push(x);
            }
        }
        return Integer.parseInt(stack.get(0));
    }

    private int cal(List<String> vs, String fun) {
        Integer res = null;
        int v0;
        int v1;
        switch (fun) {
            case "let":
                for (int i = vs.size() - 1; i >= 2; i = i - 2) {
                    String key = vs.get(i);
                    String value = vs.get(i - 1);
                    valueMap.put(key, getValue(value));
                }
                res = valueMap.get(vs.get(0));
                break;
            case "add":
                v0 = getValue(vs.get(0));
                v1 = getValue(vs.get(1));
                res = v0 + v1;
                break;
            case "mult":
                v0 = getValue(vs.get(0));
                v1 = getValue(vs.get(1));
                res = v0 * v1;
                break;
            default:
                break;

        }
        if (res == null) {
            throw new RuntimeException("没有计算规则");
        }
        return res;
    }

    private int getValue(String key) {
        Integer res;
        try {
            res = Integer.parseInt(key);
        } catch (Exception ignore) {
            res = valueMap.get(key);
        }
        if (res == null) {
            throw new RuntimeException("表达式出错");
        }
        return res;
    }

    @Test
    public void xx() {
        //int res = evaluate("(mult 3 (add 2 3))");
        int res2 = evaluate("(let x 2 (mult x (let x 3 y 4 (add x y))))");
        //System.out.println(res);
        System.out.println(res2);
    }
}
