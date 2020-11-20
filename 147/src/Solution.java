class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

class Solution {
    public ListNode insertionSortList(ListNode head) {
        ListNode dummyHead = new ListNode(Integer.MIN_VALUE);
        dummyHead.next = null;
        ListNode cur = head;
        while (cur!=null){
            ListNode next = cur.next;
            cur.next = null;
            ListNode left = dummyHead;
            while (left.next!=null&&cur.val>left.next.val){
                left = left.next;
            }
            ListNode tmp = left.next;
            left.next = cur;
            cur.next = tmp;
            cur = next;
        }
        return dummyHead.next;
    }
}