class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode dummyLeftNode = new ListNode(0);
        dummyLeftNode.next = null;
        ListNode dummyRightNode = new ListNode(0);
        dummyRightNode.next = null;
        ListNode curLeft = dummyLeftNode, curRight = dummyRightNode, cur = head;
        while (cur !=null){
            if(cur.val<x){
                curLeft.next = cur;
                curLeft = curLeft.next;
            }else{
                curRight.next = cur;
                curRight = curRight.next;
            }
            cur = cur.next;
        }
        if(dummyLeftNode.next!=null){
            curLeft.next = dummyRightNode.next;
            curRight.next = null;
            return dummyLeftNode.next;
        }else{
            curRight.next = null;
            return dummyRightNode.next;
        }
    }
}