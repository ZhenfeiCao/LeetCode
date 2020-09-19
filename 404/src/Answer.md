# 404 题解
考察点：二叉树的深度优先遍历与广度优先遍历

#### 方法一：深度优先遍历

一个节点为「左叶子」节点，当且仅当它是某个节点的左子节点，并且它是一个叶子结点。因此我们可以考虑对整棵树进行遍历，当我们遍历到节点 node 时，如果它的左子节点是一个叶子结点，那么就将它的左子节点的值累加计入答案。

遍历整棵树的方法有深度优先搜索和广度优先搜索，下面分别给出了实现代码。

```java
class Solution {
    
    public int sumOfLeftLeaves(TreeNode root){
        if(root == null)
            return 0;
        return dfs(root);
    }
    
    public int dfs(TreeNode root){
        int result = 0;
        if(root.left != null){
            if(root.left.left == null && root.left.right == null){
                result += root.left.val;
            }else{
                result += dfs(root.left);
            }
        }
        if(root.right!= null && !(root.right.left == null && root.right.right == null)){
            result += dfs(root.right);
        }
        return result;
    }
}
```

#### 方法二：广度优先遍历

```java
class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null){
            return 0;
        }
        Queue<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.offer(root);
        int result = 0;
        while(!treeNodes.isEmpty()){
            for(int i=0;i<treeNodes.size();i++){
                TreeNode current = treeNodes.poll();
                if(current.left != null){
                    // 当前节点的左孩子是叶子节点
                    if(current.left.left == null && current.left.right == null){
                        // 不需要将左孩子压入队列，因为已经是左叶子了，不会再在其的遍历中产生新的左叶子
                        result += current.left.val;
                    }else {
                        treeNodes.offer(current.left);
                    }
                }
                if(current.right !=null){
                    // 如果当前节点的右孩子不是叶子节点才需要压入队列
                    if(!(current.right.left == null && current.right.right == null))
                        treeNodes.offer(current.right);
                }
            }
        }
        return result;
    }
}
```

