package LinkedList;

public class LC142_LinkedListCycle2 {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = slow;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) {
                break;
            }
        }
        // 此时，fast和break在环上某一点相遇；
        // 假设head到环的开始点距离为x；环的开始点到相遇点为y，则有慢指针走了x+y，快指针走了2(x+y)；
        // 所以2(x+y) - (x+y) = NC, 所以(x+y) = NC；C为环的周长；
        // 此时一个指针移到head，另一个指针继续前进x，则会回到环的起始点（N圈 = x + y）；而从head开始行动的指针，也走了x距离，正好到环的起始点
        if(fast == null || fast.next == null) {
            return null;
        }
        slow = head;
        while(fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
