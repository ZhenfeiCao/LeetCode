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


public class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> averages = new ArrayList<>();
        Queue<TreeNode> trees = new LinkedList<>();
        trees.offer(root);
        while(!trees.isEmpty()){
            int currentSize = trees.size();
            double currentAverage = 0.0;
            for(int i=1;i <= currentSize;i++){
                TreeNode node = trees.poll();
                currentAverage += node.val;
                if(node.left!=null){
                    trees.offer(node.left);
                }
                if(node.right!=null){
                    trees.offer(node.right);
                }
            }
            averages.add(currentAverage/currentSize);
        }
        return averages;
    }
}
