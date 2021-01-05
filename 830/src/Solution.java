import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> largeGroupPositions(String s) {
        int len = s.length();
        int ptr = 0;
        List<List<Integer>> result = new ArrayList<>();
        while (ptr<len){
            int res = 1, begin = ptr, end = ptr+1;
            while (end<len&&s.charAt(end)==s.charAt(begin)){
                end++;
                res++;
            }
            if(res>=3) {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(begin);
                tmp.add(end-1);
                result.add(tmp);
            }
            ptr= end;
        }
        return result;
    }
}