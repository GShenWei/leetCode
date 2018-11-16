package q445_add_two_numbers_ii;

import my.ListNode;
import my.ListNodeUtils;
import org.junit.Test;

import java.util.Stack;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/8
 */
public class Q445Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        ListNode cur1 = l1;
        while (cur1 != null) {
            s1.push(cur1.val);
            cur1 = cur1.next;
        }
        ListNode cur2 = l2;
        while (cur2 != null) {
            s2.push(cur2.val);
            cur2 = cur2.next;
        }
        //进位
        int k = 0;
        ListNode next = null;

        while (!s1.isEmpty() || !s2.isEmpty() || k == 1) {
            int a = 0;
            int b = 0;
            if (!s1.isEmpty()) {
                a = s1.pop();
            }
            if (!s2.isEmpty()) {
                b = s2.pop();
            }
            int res = a + b + k;
            if (res >= 10) {
                res = res - 10;
                k = 1;
            } else {
                k = 0;
            }
            ListNode cur = new ListNode(res);
            cur.next = next;
            next = cur;
        }
        return next;
    }

    @Test
    public void xx() {
        int[] nums1 = {9, 9, 9};
        int[] nums2 = {1, 1, 1};
        ListNode l1 = ListNodeUtils.createListNode(nums1);
        ListNode l2 = ListNodeUtils.createListNode(nums2);

        ListNode listNode = addTwoNumbers(l1, l2);

        String nodeString = ListNodeUtils.getNodeString(listNode);
        System.out.println(nodeString);
    }
}
