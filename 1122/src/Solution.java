import java.util.*;

class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int max = -1;
        for(int i:arr1){
            max = Math.max(i,max);
        }
        int[] nums = new int[max+1];
        for(int i:arr1){
            nums[i]++;
        }
        int index = 0;
        for(int i:arr2){
            while (nums[i]>0){
                arr1[index] = i;
                index++;
                nums[i]--;
            }
        }
        for(int i=0;i<=max;i++){
            for(int j=1;j<=nums[i];j++){
                arr1[index]=i;
                index++;
            }
        }
        return arr1;
    }
}
