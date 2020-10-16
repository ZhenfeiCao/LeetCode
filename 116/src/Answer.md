# 94题解
考察点：二叉树的中序遍历

#### 方法一：层次遍历

思路与算法

题目本身希望我们将二叉树的每一层节点都连接起来形成一个链表。因此直观的做法我们可以对二叉树进行层次遍历，在层次遍历的过程中将我们将二叉树每一层的节点拿出来遍历并连接。

层次遍历基于广度优先搜索，它与广度优先搜索的不同之处在于，广度优先搜索每次只会取出一个节点来拓展，而层次遍历会每次将队列中的所有元素都拿出来拓展，这样能保证每次从队列中拿出来遍历的元素都是属于同一层的，因此我们可以在遍历的过程中修改每个节点的 next 指针，同时拓展下一层的新队列

```java
class Solution {
    public Node connect(Node root) {
        Queue<Node> queue = new LinkedList<>();
        if(root != null)
            queue.offer(root);
        while (!queue.isEmpty()){
            int len = queue.size();
            Node current = queue.poll();
            if(current.left != null){
                queue.offer(current.left);
                queue.offer(current.right);
            }
            for(int i=1;i<len;i++){
                Node next = queue.poll();
                current.next = next;
                current = next;
                if(current.left != null){
                    queue.offer(current.left);
                    queue.offer(current.right);
                }
            }
            current.next = null;
        }
        return root;
    }
}
```

#### 方法二： 使用已建立的 next 指针

一棵树中，存在两种类型的 `next` 指针。

1. 第一种情况是连接同一个父节点的两个子节点。它们可以通过同一个节点直接访问到，因此执行下面操作即可完成连接。

```
node.left.next = node.right
```

![](https://assets.leetcode-cn.com/solution-static/116/1.png)

2. 第二种情况在不同父亲的子节点之间建立连接，这种情况不能直接连接。

![](https://assets.leetcode-cn.com/solution-static/116/2.png)

如果每个节点有指向父节点的指针，可以通过该指针找到 `next` 节点。如果不存在该指针，则按照下面思路建立连接：

第 N 层节点之间建立 next 指针后，再建立第 N+1 层节点的 next 指针。可以通过 next 指针访问同一层的所有节点，因此可以使用第 N 层的 next 指针，为第 N+1 层节点建立 next 指针。

算法

1. 从根节点开始，由于第 00 层只有一个节点，所以不需要连接，直接为第 11 层节点建立 next 指针即可。该算法中需要注意的一点是，当我们为第 NN 层节点建立 next 指针时，处于第 N-1N−1 层。当第 NN 层节点的 next 指针全部建立完成后，移至第 NN 层，建立第 N+1N+1 层节点的 next 指针。

2. 遍历某一层的节点时，这层节点的 next 指针已经建立。因此我们只需要知道这一层的最左节点，就可以按照链表方式遍历，不需要使用队列.
3. 上面思路的伪代码如下：

```
leftmost = root
while (leftmost.left != null) {
    head = leftmost
    while (head.next != null) {
        1) Establish Connection 1
        2) Establish Connection 2 using next pointers
        head = head.next
    }
    leftmost = leftmost.left
}
```

![](https://assets.leetcode-cn.com/solution-static/116/3.png)

4. 两种类型的 `next` 指针。

   4.1 第一种情况两个子节点属于同一个父节点，因此直接通过父节点建立两个子节点的 `next` 指针即可。

   ```
   node.left.next = node.right
   ```

   ![](https://assets.leetcode-cn.com/solution-static/116/4.png)

   4.2 第二种情况是连接不同父节点之间子节点的情况。更具体地说，连接的是第一个父节点的右孩子和第二父节点的左孩子。由于已经在父节点这一层建立了 next 指针，因此可以直接通过第一个父节点的 next 指针找到第二个父节点，然后在它们的孩子之间建立连接。

   ```
   node.right.next = node.next.left
   ```

   ![](https://assets.leetcode-cn.com/solution-static/116/5.png)

5. 完成当前层的连接后，进入下一层重复操作，直到所有的节点全部连接。进入下一层后需要更新最左节点，然后从新的最左节点开始遍历该层所有节点。因为是完美二叉树，因此最左节点一定是当前层最左节点的左孩子。如果当前最左节点的左孩子不存在，说明已经到达该树的最后一层，完成了所有节点的连接。

   ![](https://assets.leetcode-cn.com/solution-static/116/6.png)

   ```java
   class Solution {
       public Node connect(Node root) {
           if (root == null) {
               return root;
           }
           
           // 从根节点开始
           Node leftmost = root;
           
           while (leftmost.left != null) {
               
               // 遍历这一层节点组织成的链表，为下一层的节点更新 next 指针
               Node head = leftmost;
               
               while (head != null) {
                   
                   // CONNECTION 1
                   head.left.next = head.right;
                   
                   // CONNECTION 2
                   if (head.next != null) {
                       head.right.next = head.next.left;
                   }
                   
                   // 指针向后移动
                   head = head.next;
               }
               
               // 去下一层的最左的节点
               leftmost = leftmost.left;
           }
           
           return root;
       }
   }
   
   ```

   #### 方法三： 方法二的递归实现

   ```java
   class Solution {
   
       public Node connect(Node root){
           if(root == null || root.left == null)
               return root;
           root.left.next = root.right;
           if(root.next == null)
               root.right.next = null;
           else
               root.right.next = root.next.left;
           connect(root.left);
           connect(root.right);
           return root;
       }
   
   }
   ```
