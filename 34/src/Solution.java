class Solution {
    public int[] searchRange(int[] nums, int target) {
        if(nums.length==0)
            return new int[]{-1, -1};
        return searchRangeRecur(nums, 0, nums.length-1, target);
    }

    public int[] searchRangeRecur(int[] nums, int left, int right, int target){
        if(left == right){
            if(nums[left] == target)
                return new int[]{left,right};
            else
                return new int[]{-1,-1};
        }
        int middle = (left + right) / 2;
        if(nums[middle] <target){
            return searchRangeRecur(nums, middle+1, right, target);
        } else if (nums[middle] >target){
            return searchRangeRecur(nums, left, middle, target);
        } else{
            int[] leftPos = searchRangeRecur(nums, left, middle, target);
            int[] rightPos = searchRangeRecur(nums, middle+1, right, target);
            int tmp = rightPos[0]==-1?leftPos[1]:rightPos[1];
            return new int[]{leftPos[0], tmp};
        }
    }

}