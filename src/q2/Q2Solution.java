package q2;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Solution
 * @description
 * @date 2017/5/24
 */

public class Q2Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p = l1;
        ListNode q = l2;
        ListNode p2;
        ListNode q2;
        int sum = 0;
        int carry = 0;
        while (true) {
            sum = p.val + q.val + carry;
            p.val = sum % 10;
            carry = sum / 10;
            p2 = p.next;
            q2 = q.next;
            if (!(p2 == null && q2 == null && carry == 0)) {
                if (p2 == null) {
                    p2 = new ListNode(0);
                    p.next = p2;
                    p = p.next;
                } else {
                    p = p2;
                }
                q = q2 == null ? new ListNode(0) : q2;
            } else {
                break;
            }
        }
        return l1;
    }

    @Test
    public void oo() {
        ListNode l1 = new ListNode(7);
        l1.next=new ListNode(9);
        ListNode l2 = new ListNode(7);
        ListNode listNode = addTwoNumbers(l2, l1);
        System.out.println(listNode);
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}




