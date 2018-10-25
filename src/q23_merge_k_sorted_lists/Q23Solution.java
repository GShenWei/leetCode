package q10;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q10Solution
 * @description
 * @date 2017/12/13
 */
public class Q23Solution {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        List<Integer> ints = new ArrayList<>();
        for (ListNode l : lists) {
            if (l == null) {
                continue;
            } else {
                while (l != null) {
                    ints.add(l.val);
                    l = l.next;
                }
            }
        }
        Collections.sort(ints);
        ListNode head = new ListNode(0);
        ListNode last = null;
        for (int i : ints) {
            if (last == null) {
                last = new ListNode(i);
                head.next = last;
            } else {
                last.next = new ListNode(i);
                last = last.next;
            }

        }
        return head.next;
    }


    @Test
    public void oo() {
        ListNode[] list = new ListNode[2];
        list[0] = new ListNode(0);
        list[0].next = new ListNode(1);
        list[1] = new ListNode(2);
        ListNode listNode = mergeKLists(list);
        System.out.println(listNode);
    }


    @Test
    public void cc() {
        ListNode l = new ListNode(20);
        l.next = new ListNode(30);
        l = l.next;

        System.out.println(l);
    }
}
