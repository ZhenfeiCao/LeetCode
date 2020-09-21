# 538题解
考察点：二叉搜索树的反序中序遍历

#### 方法一：反序中序遍历

二叉搜索树是一棵空树，或者是具有下列性质的二叉树：

若它的左子树不空，则左子树上所有节点的值均小于它的根节点的值；

若它的右子树不空，则右子树上所有节点的值均大于它的根节点的值；

它的左、右子树也分别为二叉搜索树。

由这样的性质我们可以发现，二叉搜索树的中序遍历是一个单调递增的有序序列。如果我们反序地中序遍历该二叉搜索树，即可得到一个单调递减的有序序列。

本题中要求我们将每个节点的值修改为原来的节点值加上所有大于它的节点值之和。这样我们只需要反序中序遍历该二叉搜索树，记录过程中的节点值之和，并不断更新当前遍历到的节点的节点值，即可得到题目要求的累加树。

```java
class Solution {
    int sum =0;
    public TreeNode convertBST(TreeNode root) {
        midOrder(root);
        return root;
    }

    public void midOrder(TreeNode root){
        if(root == null)
            return;
        midOrder(root.right);
        sum += root.val;
        root.val = sum;
        midOrder(root.left);
    }

}
```

