import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        getSubset(result, nums, 0 ,new ArrayList<Integer>());
        return result;
    }

    public void getSubset(List<List<Integer>> result, int[] nums, int n, ArrayList<Integer> current){
        if( n == nums.length){
            return;
        }
        for(int i=n;i<nums.length;i++) {
            int currentNum = nums[i];
            ArrayList<Integer> newCurrent = new ArrayList<>(current);
            newCurrent.add(currentNum);
            result.add(newCurrent);
            getSubset(result, nums,i+1 ,newCurrent);
        }
    }
    
}