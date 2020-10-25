class Solution {
    public int longestMountain(int[] A) {
        int result = 0,i = 1;
        while (i<A.length-1){
            int left = i-1,right = i+1;
            while (left>=0&&A[left]<A[left+1])
                left--;
            while (right<A.length&&A[right] < A[right-1])
                right++;
            if(left!=i-1&&right!=i+1)
                result = Math.max(result,right-left-1);
            i = right;
        }
        return result;
    }
}
