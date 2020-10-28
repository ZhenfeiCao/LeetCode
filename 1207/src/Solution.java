import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer,Integer> times = new HashMap<>();
        Set<Integer> timeOfTimes = new HashSet<>();
        for(int i:arr){
            if(!times.containsKey(i))
                times.put(i,1);
            else
                times.put(i,times.get(i)+1);
        }
        for(int i :times.values()){
            if(!timeOfTimes.add(i))
                return false;
        }
        return true;
    }
}
