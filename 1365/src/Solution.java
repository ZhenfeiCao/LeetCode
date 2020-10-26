import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] result = new int[nums.length];
        HashMap<Integer,Integer> map = new HashMap<>();
        int[] sortNums = new int[nums.length];
        for(int k=0;k<nums.length;k++){
            sortNums[k] = nums[k];
        }
        Arrays.sort(sortNums);
        int i=0;
        while (i<sortNums.length){
            if(i>=1&&sortNums[i]==sortNums[i-1]){

            }else {
                map.put(sortNums[i], i);
            }
            i++;
        }
        for(i =0;i<nums.length;i++){
            result[i] = map.get(nums[i]);
        }
        return result;
    }
}
