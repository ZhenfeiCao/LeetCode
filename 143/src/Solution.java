import java.util.ArrayList;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public void reorderList(ListNode head) {
        if(head == null)
            return;
        ListNode middle = findMiddleNode(head);
        ListNode afterMiddle = middle.next;
        middle.next = null;
        ListNode newHead = reverseList(afterMiddle);
        mergeList(head, newHead);
    }

    public ListNode findMiddleNode(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head){
        ListNode pre = null;
        ListNode current = head;
        while (current != null){
            ListNode after = current.next;
            current.next = pre;
            pre = current;
            current = after;
        }
        return pre;
    }

    public void mergeList(ListNode head1, ListNode head2){
        ListNode newHead1 = null;
        ListNode newHead2 = null;
        while (head1 != null && head2!=null){
            newHead2 = head2.next;
            newHead1 = head1.next;

            head1.next = head2;
            head1 = newHead1;

            head2.next = head1;
            head2 = newHead2;
        }
    }

}
