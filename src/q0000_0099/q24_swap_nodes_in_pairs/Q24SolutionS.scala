package q0000_0099.q24_swap_nodes_in_pairs

import my.{ListNode, ListNodeUtils}
import org.junit.Test

class Q24SolutionS {

  def swapPairs(head: ListNode): ListNode = {
    val dummyHeader = new ListNode(0)
    dummyHeader.next = head
    var p = dummyHeader
    while (p.next != null && p.next.next != null) {
      val node1 = p.next
      val node2 = node1.next
      node1.next = node2.next
      node2.next = node1
      p.next = node2
      p = node1
    }
    dummyHeader.next
  }

  @Test
  def xx(): Unit = {
    val a = Array(1)
    val node = ListNodeUtils.createListNode(a)
    val node2 = swapPairs(node)
    val str = ListNodeUtils.getNodeString(node2)
    println(str)
  }
}

