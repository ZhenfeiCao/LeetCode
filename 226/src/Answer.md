# 226 题解
考察点：递归遍历二叉树

#### 方法：递归

每个子树的根节点都说：“先翻转我的左右孩子吧！”

结果就是，位于树的底部的、左右孩子都是 null 的子树，先被翻转。

向上返回的过程中，子树被一个个翻转……递归全部出栈时，整棵树就翻转好了。

那，一个子树的翻转怎么实现呢？交换它的左右子节点（左右子树）。每个子树都这么做。

于是，问题在递归出栈时，被解决。

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}
```

先交换左右子树，它们内部还没翻转好，没事，交给递归去翻转。

即，把交换的操作，放在递归调用之前。

问题在递归压栈时，被解决。

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        tranvers(root);
        return root;
    }

    public void tranvers(TreeNode root){
        if(root == null){
            return;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        tranvers(root.left);
        tranvers(root.right);
    }
}
```

