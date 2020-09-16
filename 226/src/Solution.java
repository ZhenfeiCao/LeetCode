class TreeNode {
    int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}

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
