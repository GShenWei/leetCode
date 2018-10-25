package q341_flatten_nested_list_iterator;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/8/20
 */
public class Q341Solution {

}

class NestedIterator implements Iterator<Integer> {
    private Stack<NestedInteger> stack = new Stack<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        //由于栈是先进后出，所以需要将topList倒置放入栈中
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            NestedInteger in = nestedList.get(i);
            stack.push(in);
        }
    }

    @Override
    public Integer next() {
        //只要有下一个，那么栈顶就是所求值
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        //循环，只要栈还不是空的，直到栈顶为整数时返回
        while (!stack.isEmpty()) {
            NestedInteger top = stack.peek();
            if (top.isInteger()) {
                return true;
            } else {
                //如果第一个不是整数，那么将其出栈
                top = stack.pop();
                List<NestedInteger> topList = top.getList();
                //由于栈是先进后出，所以需要将topList倒置放入栈中
                for (int i = topList.size() - 1; i >= 0; i--) {
                    NestedInteger in = topList.get(i);
                    stack.push(in);
                }
            }
        }
        return false;
    }

}



