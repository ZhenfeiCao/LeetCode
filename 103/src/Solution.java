import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> curLevel = new LinkedList<>();
        if(root!=null)
            curLevel.offer(root);
        boolean isOddLevel = true;
        while (!curLevel.isEmpty()){
            List<Integer> tmp = new ArrayList<>();
            int size = curLevel.size();
            if(isOddLevel){
                for(int i=0;i<size;i++){
                    TreeNode tmpTree = curLevel.poll();
                    if(tmpTree.left!=null) curLevel.offer(tmpTree.left);
                    if(tmpTree.right!=null) curLevel.offer(tmpTree.right);
                    tmp.add(tmpTree.val);
                }
            }else{
                for(int i=0;i<size;i++){
                    TreeNode tmpTree = curLevel.poll();
                    if(tmpTree.left!=null) curLevel.offer(tmpTree.left);
                    if(tmpTree.right!=null) curLevel.offer(tmpTree.right);
                    tmp.add(0,tmpTree.val);
                }
            }
            isOddLevel = !isOddLevel;
            res.add(tmp);
        }
        return res;
    }
}
