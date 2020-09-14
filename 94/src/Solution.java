import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> nums = new ArrayList<>();
        Stack<TreeNode> tree = new Stack<>();
        while (root!=null || !tree.isEmpty()){
            while(root!=null){
                tree.push(root);
                root = root.left;
            }
            root = tree.pop();
            nums.add(root.val);
            root = root.right;
        }
        return nums;
    }
}
