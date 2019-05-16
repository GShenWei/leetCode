package q0000_0099.q25_reverse_nodes_in_k_group;

import my.ListNode;
import my.ListNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q25Solution
 * @description
 * @date 2017/12/19
 */
public class Q25Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k == 1) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode cur = head;
        ListNode pre = dummy;
        ListNode groupPre = null;
        ListNode groupEnd;
        int i = 0;
        while (cur != null) {
            i++;
            if (i == 1) {
                groupPre = pre;
            }
            ListNode next = cur.next;
            if (i == k) {
                groupEnd = cur;
                cur = reverse(groupPre, groupEnd, k);
                i = 0;
            }
            pre = cur;
            cur = next;
        }
        return dummy.next;
    }

    private ListNode reverse(ListNode headPre, ListNode end, int k) {
        if (end == null) {
            return null;
        }
        ListNode totalNext = end.next;
        ListNode firstHead = headPre.next;
        ListNode cur = headPre.next;

        headPre.next = end;
        ListNode pre = null;
        while (k-- > 0) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        firstHead.next = totalNext;
        return firstHead;
    }

    @Test
    public void tt() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
        ListNode node = ListNodeUtils.createListNode(nums);
        ListNode listNode = reverseKGroup(node, 2);
        ListNodeUtils.printNodeString(listNode);
    }
}
