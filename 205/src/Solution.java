import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isIsomorphic(String s, String t) {
        int len = s.length();
        Map<Character, Character> alt = new HashMap<>();
        for(int i=0;i<len;i++){
            char curS = s.charAt(i);
            char curT = t.charAt(i);
            if(!alt.containsKey(curS))
                if(alt.containsValue(curT))
                    return false;
                else
                    alt.put(curS, curT);
            else{
                if(alt.get(curS)!=curT)
                    return false;
            }
        }
        return true;
    }
}