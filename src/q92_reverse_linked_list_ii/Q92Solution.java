package q92_reverse_linked_list_ii;

import my.ListNode;
import my.ListNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/8
 */
public class Q92Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode pre = null;
        ListNode cur = head;
        int k = 1;
        ListNode beforeWindow = null;
        ListNode curWindow = null;
        ListNode nextWindow = null;

        ListNode res = head;
        while (cur != null && k <= n) {
            ListNode next = cur.next;
            if (k == m) {
                beforeWindow = pre;
                curWindow = cur;
            }
            if (k == n) {
                nextWindow = next;
                curWindow.next = nextWindow;
                if (m == 1) {
                    res = cur;
                } else {
                    beforeWindow.next = cur;
                }
            }
            if (k > m && k <= n) {
                cur.next = pre;
            }
            pre = cur;
            cur = next;
            k++;
        }
        return res;
    }

    @Test
    public void xx() {
        int[] nums = {1, 2, 3, 4};
        ListNode listNode = ListNodeUtils.createListNode(nums);
        ListNode res = reverseBetween(listNode, 1, 4);
        String nodeString = ListNodeUtils.getNodeString(res);
        System.out.println(nodeString);
    }
}
