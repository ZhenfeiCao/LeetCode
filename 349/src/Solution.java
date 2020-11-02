import java.util.HashSet;
import java.util.Set;

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> Nums1 = new HashSet<>();
        Set<Integer> Nums2 = new HashSet<>();
        for(int i : nums1){
            Nums1.add(i);
        }
        for(int j : nums2){
            if(Nums1.contains(j)){
                Nums2.add(j);
            }
        }
        int [] result = new int[Nums2.size()];
        int index = 0;
        for(int i:Nums2){
            result[index] = i;
            index++;
        }
        return result;
    }
}
