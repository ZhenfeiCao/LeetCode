import java.util.ArrayList;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

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
