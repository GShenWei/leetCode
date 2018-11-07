package q25_reverse_nodes_in_k_group;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q25Solution
 * @description
 * @date 2017/12/19
 */

class ListNode {
    //TODO 未解决
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class Q25Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k == 1) {
            return head;
        }
        ListNode h = new ListNode(Integer.MIN_VALUE);
        h.next = head;
        ListNode now = head;
        ListNode pre = h;
        ListNode lastGroupFoot = h;
        ListNode thisGroupFoot = null;
        ListNode thisGroupHead = null;
        int i = 0;
        while (now != null) {
            i++;
            ListNode next = now.next;
            if(i==k|| next==null){
                thisGroupHead = now;
            }
            if (i == k) {
                i = 0;
                if (lastGroupFoot != null) {
                    lastGroupFoot.next = now;
                }
                lastGroupFoot = thisGroupFoot;
            } else if (i == 1) {
                thisGroupFoot = now;
            }
            if (i != 1) {
                now.next = pre;
            }
            pre = now;
            now = next;
        }
        if (lastGroupFoot != null) {
            lastGroupFoot.next = thisGroupHead;
            if(thisGroupFoot != null) {
                thisGroupFoot.next = null;
            }
        }
        return h.next;
    }

    @Test
    public void tt() {
        ListNode h = new ListNode(0);
        ListNode n1 = h.next = new ListNode(1);
        /*ListNode n2 = n1.next = new ListNode(2);
        ListNode n3 = n2.next = new ListNode(3);
        ListNode n4 = n3.next = new ListNode(4);
        ListNode n5 = n4.next = new ListNode(5);
        ListNode n6 = n5.next = new ListNode(6);
        ListNode n7 = n6.next = new ListNode(7);*/
        ListNode listNode = reverseKGroup(h, 2);
        System.out.println(listNode);
    }
}
