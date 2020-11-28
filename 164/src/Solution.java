import java.util.Arrays;

class Solution {
    public int maximumGap(int[] nums) {
        if(nums.length<2)
            return 0;
        int max = 0;
        int[] buckets = new int[10];
        int[] buffer = new int[nums.length];
        for(int i:nums){
            max = Math.max(i,max);
        }
        for(int i=1;max/i>0;i=i*10){
            for(int j = 0; j < nums.length; j ++){
                buckets[nums[j] / i % 10] ++;
            }
            for(int j = 1; j < buckets.length; j ++){
                buckets[j] += buckets[j - 1];
            }
            for(int j = nums.length - 1; j >= 0; j --){
                buffer[-- buckets[nums[j] / i % 10]] = nums[j];
            }
            for(int j = 0; j < nums.length; j ++){
                nums[j] = buffer[j];
            }
            Arrays.fill(buckets, 0);
        }
        int res = 0;
        for(int i=1;i<nums.length;i++){
            res = Math.max(res, Math.abs(nums[i] - nums[i-1]));
        }
        return res;
    }
}
