class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode sortList(ListNode head) {
        ListNode middle = findMiddle(head);
        if(middle == null)
            return head;
        ListNode tmp = middle.next;
        middle.next = null;
        ListNode head1 = sortList(head);
        ListNode head2 = sortList(tmp);
        return mergeList(head1, head2);
    }

    public ListNode findMiddle(ListNode head){
        ListNode slow = head;
        if(slow == null)
            return null;
        ListNode fast = slow.next;
        if(fast == null)
            return null;
        while (fast!=null&&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode mergeList(ListNode head1, ListNode head2){
        ListNode dummyHead = new ListNode(0);
        ListNode cur1 = head1, cur2 = head2, cur = dummyHead;
        while (cur1!=null&&cur2!=null){
            if(cur1.val<cur2.val){
                cur.next = cur1;
                cur1 = cur1.next;
            }else{
                cur.next = cur2;
                cur2 = cur2.next;
            }
            cur = cur.next;
        }
        if(cur1!=null)
            cur.next = cur1;
        if(cur2!=null)
            cur.next = cur2;
        return dummyHead.next;
    }
}