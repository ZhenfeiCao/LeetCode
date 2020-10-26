class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

class Solution {
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null)
            return true;
        ListNode middle = findMiddle(head);
        ListNode afterMiddle = middle.next;
        middle.next =null;
        ListNode newHead = reverseList(afterMiddle);
        ListNode tmpHead = head;
        while (newHead!=null){
            if(tmpHead.val == newHead.val){
                tmpHead = tmpHead.next;
                newHead = newHead.next;
            }else{
                return false;
            }
        }
        return true;
    }

    public ListNode findMiddle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        while(fast.next!=null&&fast.next.next!=null){
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

}
