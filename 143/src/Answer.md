# 143题解
考察点：重排链表

#### 方法一：线性表

因为链表不支持下标访问，所以我们无法随机访问链表中任意位置的元素。

因此比较容易想到的一个方法是，我们利用线性表存储该链表，然后利用线性表可以下标访问的特点，直接按顺序访问指定元素，重建该链表即可。

```java
class Solution {
    public void reorderList(ListNode head) {
        ArrayList<ListNode> nodes = new ArrayList<>();
        ListNode tmp = head;
        while (tmp!=null){
            nodes.add(tmp);
            tmp = tmp.next;
        }
        ListNode dummyHead = new ListNode(0, head);
        for(int i=0;i<(nodes.size()+1)/2;i++){
            dummyHead.next = nodes.get(i);
            if(i!=nodes.size()-1-i) {
                nodes.get(i).next = nodes.get(nodes.size() - 1 - i);
            }
            dummyHead = nodes.get(nodes.size() - 1 - i);
        }
        dummyHead.next = null;
    }
}
```

#### 方法二：寻找链表中点 + 链表逆序 + 合并链表
注意到目标链表即为将原链表的左半端和反转后的右半端合并后的结果。

这样我们的任务即可划分为三步：

1. 找到原链表的中点（参考「876. 链表的中间结点」）。
   - 我们可以使用快慢指针来 $O(N)$ 地找到链表的中间节点。
2. 将原链表的右半端反转（参考「206. 反转链表」）。
   - 我们可以使用迭代法实现链表的反转。
3. 将原链表的两端合并。
   - 因为两链表长度相差不超过 $1$，因此直接合并即可。


```java
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
```

