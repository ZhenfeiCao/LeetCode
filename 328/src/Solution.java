class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode oddEvenList(ListNode head) {
        if(head == null)
            return null;
        ListNode oddNode = head;
        ListNode evenNode = oddNode.next;
        ListNode evenHead = evenNode;
        ListNode nextOdd;
        ListNode nextEven;
        while (evenNode!=null&&evenNode.next!=null){
            nextOdd = evenNode.next;
            nextEven = nextOdd.next;
            oddNode.next = nextOdd;
            evenNode.next = nextEven;
            oddNode = nextOdd;
            evenNode = nextEven;
        }
        oddNode.next = evenHead;
        return head;
    }
}

// 1 2 3 4 5 6 null