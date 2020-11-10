import java.util.Arrays;

class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        if(n<=1)
            return;
        int lastUp;
        for(lastUp = n-2;lastUp>=0;lastUp--){
            if(nums[lastUp]<nums[lastUp+1]){
                break;
            }
        }
        if(lastUp<0){
            Arrays.sort(nums);
        }else {
            int swapIndex;
            for(swapIndex = n-1;swapIndex>lastUp;swapIndex--){
                if(nums[swapIndex] > nums[lastUp])
                    break;
            }
            int tmpVal = nums[swapIndex];
            nums[swapIndex] = nums[lastUp];
            nums[lastUp] = tmpVal;
            Arrays.sort(nums, lastUp + 1, n);
        }
    }
}
