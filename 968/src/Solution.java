class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    int result = 0;
    public int minCameraCover(TreeNode root) {
        int rootState = minCam(root);
        if(rootState == 2){
            result++;
        }
        return result;
    }

    public int minCam(TreeNode root) {
        if(root == null)
            return 1;
        int left = minCam(root.left);
        int right = minCam(root.right);
        if(Math.max(left, right) == 2){
            result++;
            return 0;
        }else{
            return Math.min(left, right) + 1;
        }
    }
}