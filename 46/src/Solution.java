import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        for(int i:nums){
            list.add(i);
        }
        addNumber(0, list, result, nums.length-1);
        return result;
    }

    public void addNumber(int current, ArrayList<Integer> list, List<List<Integer>> nums, int n){
        if(current == n){
            nums.add(new ArrayList<>(list));
        }
        for(int i=current;i<list.size();i++){
            // 用交换模拟将未放置的元素放在空位上的操作
            Collections.swap(list,current,i);
            // 继续排列下一个位置
            addNumber(current+1, list,nums,n);
            // 将两个数字的位置换回去，进行回溯，不影响从上一个节点开始的重新摆放
            Collections.swap(list,current,i);
        }
    }
}
