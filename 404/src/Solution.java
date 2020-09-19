import java.util.LinkedList;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

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
                    if(current.left.left == null && current.left.right == null){
                        result += current.left.val;
                    }else {
                        treeNodes.offer(current.left);
                    }
                }
                if(current.right !=null){
                    if(!(current.right.left == null && current.right.right == null))
                        treeNodes.offer(current.right);
                }
            }
        }
        return result;
    }
}