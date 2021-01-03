import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public int lastStoneWeight(int[] stones) {
        ArrayList<Integer> allStones = new ArrayList<>();
        for(int i:stones){
            allStones.add(i);
        }
        Collections.sort(allStones);
        int ptr = stones.length-1;
        while (ptr>0){
            if(allStones.get(ptr)==allStones.get(ptr-1)){
                ptr = ptr-2;
            }else{
                int remain = Math.abs(allStones.get(ptr)-allStones.get(ptr-1));
                if(ptr == 1){
                    allStones.add(0, remain);
                }else {
                    insert(allStones, remain, 0, ptr - 2);
                }
                ptr = ptr - 1;
            }
        }
        return ptr==0?allStones.get(0):0;
    }

    public void insert(ArrayList<Integer> nums, int val, int left, int right){
        if(left == right) {
            if(val>nums.get(left)) {
                nums.add(left+1, val);
            }else{
                nums.add(left, val);
            }
            return;
        }
        int middle = (left+right)/2;
        if(val > nums.get(middle)){
            insert(nums, val, middle+1, right);
        }else if(val < nums.get(middle)){
            insert(nums, val, left, middle);
        }else{
            nums.add(middle, val);
        }
    }

    public static void main(String[] args){
        Solution s = new Solution();
        int[] a = new int[]{316,157,73,106,771,828,46,212,926,604,600,992,71,51,477,869,425,405,859,924,45,187,283,590,303,66,508,982,464,398};
        s.lastStoneWeight(a);
    }

}