class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {

    int sum = 0;

    public int sumNumbers(TreeNode root) {
        if(root != null)
            sum(root, 0);
        return sum;
    }

    public void sum(TreeNode root, int currentSum){
        currentSum = currentSum*10 + root.val;
        if(root.left==null&&root.right==null){
            sum+=currentSum;
        }else if(root.left == null){
            sum(root.right, currentSum);
        }else if(root.right == null){
            sum(root.left, currentSum);
        }else{
            sum(root.left, currentSum);
            sum(root.right, currentSum);
        }
    }

}
