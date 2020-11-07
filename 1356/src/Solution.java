import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public int[] sortByBits(int[] arr) {
        int[] result = new int[arr.length];
        Arrays.sort(arr);
        int index = 0;
        int[] tmp = new int[arr.length];
        for(int i=0;i<arr.length;i++){
            tmp[i] = arr[i];
            if(arr[i] == 0){
                index++;
            }
        }
        while (index<result.length){
            for(int i=0;i<arr.length;i++){
                if(tmp[i]==0){
                    continue;
                }
                int currentNum = tmp[i];
                int newNum = currentNum & (currentNum-1);
                tmp[i] = newNum;
                if(newNum == 0){
                    result[index] = arr[i];
                    index++;
                }
            }
        }
        return result;
    }
}
