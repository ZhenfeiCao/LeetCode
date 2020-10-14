import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    public List<String> commonChars(String[] A) {
        if(A.length == 0)
            return new ArrayList<>();
        HashMap<Character,Integer> chars = new HashMap<>();
        HashMap<Character,Integer> tmpChars = new HashMap<>();
        String s = A[0];
        for(int i=0;i<s.length();i++){
            if(chars.containsKey(s.charAt(i))){
                chars.put(s.charAt(i),chars.get(s.charAt(i))+1);
            }else{
                chars.put(s.charAt(i), 1);
            }
        }
        for(int i=1;i<A.length;i++){
            String tmp = A[i];
            for(int j=0;j<tmp.length();j++){
                if(chars.containsKey(tmp.charAt(j))&&chars.get(tmp.charAt(j))!=0){
                    if(tmpChars.containsKey(tmp.charAt(j))){
                        tmpChars.put(tmp.charAt(j),tmpChars.get(tmp.charAt(j))+1);
                    }else{
                        tmpChars.put(tmp.charAt(j), 1);
                    }
                }
            }
            for(Character c:chars.keySet()){
                if(chars.get(c)!=0) {
                    if(!tmpChars.keySet().contains(c)){
                        chars.put(c, 0);
                    }else {
                        int min = Math.min(chars.get(c), tmpChars.get(c));
                        chars.put(c, min);
                    }
                }
            }
            tmpChars.clear();
        }
        ArrayList<String> result = new ArrayList<>();
        for(Character c:chars.keySet()){
            for(int i=0;i<chars.get(c);i++){
                result.add(c+"");
            }
        }
        return result;
    }

    public static void main(String[] args){
        String[] strs = new String[3];
        strs[0] = "bella";
        strs[1] = "label";
        strs[2] = "roller";
        Solution s = new Solution();
        s.commonChars(strs);
    }

}
