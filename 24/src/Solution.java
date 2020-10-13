class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode tmp = head.next.next;
        ListNode tmp2 = head.next;
        tmp2.next = head;
        head.next = swapPairs(tmp);
        return tmp2;
    }
}