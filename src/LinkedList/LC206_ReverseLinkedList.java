package LinkedList;

public class LC206_ReverseLinkedList {
    // Given the head of a singly linked list, reverse the list, and return the reversed list.
    public ListNode reverseList(ListNode head) {

//        if(head == null || head.next == null) {
//            return head;
//        }
        ListNode curr = head;
        ListNode pre = null;
        while(curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
