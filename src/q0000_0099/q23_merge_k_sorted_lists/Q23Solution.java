package q0000_0099.q10;

import my.ListNode;
import my.ListNodeUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q10Solution
 * @description
 * @date 2017/12/13
 */
public class Q23Solution {

    /*public ListNode mergeKLists(ListNode[] lists) {
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
    }*/


    class Pair {
        int index;
        ListNode node;

        public Pair(int index, ListNode node) {
            this.index = index;
            this.node = node;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        //维护一个优先队列
        PriorityQueue<Pair> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1.node.val > o2.node.val) {
                return 1;
            } else if (o1.node.val < o2.node.val) {
                return -1;
            }
            return 0;
        });

        List<ListNode> newListNodes = new ArrayList<>();
        for (ListNode n : lists) {
            if (n != null) {
                newListNodes.add(n);
            }
        }
        int sz = newListNodes.size();
        ListNode resPre = new ListNode(-1);
        ListNode pre = resPre;

        //用来保存某个链表是不是空掉了
        Set<Integer> notNullIndex = new HashSet<>();
        for (int l = 0; l < sz; l++) {
            notNullIndex.add(l);
        }

        //即将被载入到队列中的listNode的index
        int i = 0;
        while (!notNullIndex.isEmpty()) {
            ListNode node = newListNodes.get(i);
            if (queue.size() < sz) {
                if (node != null) {
                    queue.add(new Pair(i, node));
                    newListNodes.set(i, node.next);
                }
                i++;
                if (i == sz) {
                    i = queue.peek().index;
                }
            } else if (queue.size() == sz) {
                if (node == null) {
                    notNullIndex.remove(i);
                    i++;
                } else {
                    Pair minPair = queue.poll();
                    queue.add(new Pair(i, node));
                    newListNodes.set(i, node.next);
                    pre.next = minPair.node;
                    pre = minPair.node;
                    pre.next = null;
                    //下次应该入队的是目前为止，队列中最小的元素所在的链表中的数据
                    if (queue.peek() != null) {
                        i = queue.peek().index;
                    }
                }
            }
            i = i % sz;
        }

        while (!queue.isEmpty()) {
            Pair min = queue.poll();
            ListNode newNode = min.node;
            pre.next = newNode;
            pre = newNode;
            pre.next = null;
        }
        return resPre.next;
    }


    @Test
    public void oo() {
        int[] nums1 = {1, 4, 5};
        int[] nums2 = {0, 2};

        ListNode listNode1 = ListNodeUtils.createListNode(nums1);
        ListNode listNode2 = ListNodeUtils.createListNode(nums2);

        ListNode[] listNodes = {listNode1, listNode2};

        ListNode listNode = mergeKLists(listNodes);

        ListNodeUtils.printNodeString(listNode);
    }


    @Test
    public void cc() {
        int[] nums1 = {-5, -2, 0, 1, 1, 2};
        int[] nums2 = {-7, -6, -3};
        int[] nums3 = {-8, -7, -4, -4, 0, 2, 3, 4};
        int[] nums4 = {};

        ListNode listNode1 = ListNodeUtils.createListNode(nums1);
        ListNode listNode2 = ListNodeUtils.createListNode(nums2);
        ListNode listNode3 = ListNodeUtils.createListNode(nums3);
        ListNode listNode4 = ListNodeUtils.createListNode(nums4);
        ListNode[] listNodes = {listNode1, listNode2, listNode3, listNode4};

        ListNode listNode = mergeKLists(listNodes);

        ListNodeUtils.printNodeString(listNode);
    }
}
