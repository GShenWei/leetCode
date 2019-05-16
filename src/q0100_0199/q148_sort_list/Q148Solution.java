package q0100_0199.q148_sort_list;

import my.ListNode;
import my.ListNodeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/9
 */
public class Q148Solution {
    public ListNode sortList(ListNode head) {
        ListNode d = new ListNode(0);
        d.next = head;
        ListNode c = d.next;
        int listSz = 0;
        while (c != null) {
            listSz++;
            c = c.next;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        for (int sz = 1; sz <= listSz; sz += sz) {
            ListNode pre = dummyHead;
            while (pre != null) {
                ListNode l = pre.next;
                ListNode m = getNextN(l, sz - 1);
                ListNode h = getNextN(l, sz - 1 + sz);
                List<ListNode> merge = merge(l, m, h);
                pre.next = merge.get(0);
                pre = merge.get(1);
            }
        }
        return dummyHead.next;
    }

    private ListNode getNextN(ListNode h, int n) {
        ListNode res = h;
        while (n-- > 0 && res != null && res.next != null) {
            res = res.next;
        }
        return res;
    }

    /**
     * 返回两个节点,0为归并后的第一个节点,1为归并后的最后一个节点
     *
     * @param l
     * @param m
     * @param h
     * @return
     */
    private List<ListNode> merge(ListNode l, ListNode m, ListNode h) {
        List<ListNode> res = new ArrayList<>();
        if (l == null || m == null || h == null) {
            res.add(l);
            res.add(h);
            return res;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode pre = dummyHead;
        ListNode nextLastNode = h.next;
        ListNode lastNode = null;
        h.next = null;
        ListNode n1 = l;
        ListNode n2 = m.next;
        m.next = null;
        while (n1 != null || n2 != null) {
            if (n1 != null && n2 != null) {
                if (n1.val > n2.val) {
                    pre.next = n2;
                    pre = n2;
                    n2 = n2.next;
                } else {
                    pre.next = n1;
                    pre = n1;
                    n1 = n1.next;
                }
            } else if (n1 == null) {
                pre.next = n2;
                pre = n2;
                lastNode = n2;
                n2 = n2.next;
            } else {
                pre.next = n1;
                pre = n1;
                lastNode = n1;
                n1 = n1.next;
            }
        }
        lastNode.next = nextLastNode;
        res.add(dummyHead.next);
        res.add(lastNode);
        return res;
    }

    @Test
    public void fd() {
        /*int[] n = {1, 4, 5, 7, 2, 6, 7, 1, 2, 5};
        ListNode head = ListNodeUtils.createListNode(n);
        ListNode m = head.next.next.next;//第一个7
        ListNode h = head.next.next.next.next.next.next;//第二个7*/

        int[] n = {4, 1, 2};
        ListNode head = ListNodeUtils.createListNode(n);
        ListNode m = head.next;
        ListNode h = head.next.next;
        List<ListNode> merge = merge(head, m, h);
        ListNodeUtils.printNodeString(merge.get(0));

    }

    @Test
    public void xxx() {
        int[] n = {2,1};
        ListNode head = ListNodeUtils.createListNode(n);
        ListNode listNode = sortList(head);
        ListNodeUtils.printNodeString(listNode);
    }
}
