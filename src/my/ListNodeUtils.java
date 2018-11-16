package my;

import q92_reverse_linked_list_ii.Q92Solution;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/8
 */
public class ListNodeUtils {
    public static ListNode createListNode(int nums[]) {
        ListNode pre = null;
        ListNode head = null;
        for (int num : nums) {
            ListNode cur = new ListNode(num);
            if (head == null) {
                head = cur;
            }
            if (pre != null) {
                pre.next = cur;
            }
            pre = cur;
        }
        return head;
    }

    public static String getNodeString(ListNode node) {
        StringBuilder sb = new StringBuilder();
        ListNode cur = node;
        while (cur != null) {
            sb.append(cur.val).append(",");
            cur = cur.next;
        }
        return sb.toString();
    }

    public static void printNodeString(ListNode node) {
        System.out.println(getNodeString(node));
    }
}
