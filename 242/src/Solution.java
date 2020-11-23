import java.util.HashMap;

class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length()!=t.length())
            return false;
        HashMap<Character, Integer> sChar = new HashMap<>();
        for(int i=0;i<s.length();i++){
            char tmpChar = s.charAt(i);
            sChar.put(tmpChar,sChar.getOrDefault(tmpChar, 0)+1);
        }
        for(int i=0;i<t.length();i++){
            char tmpChar = t.charAt(i);
            sChar.put(tmpChar,sChar.getOrDefault(tmpChar, 0)-1);
            if (sChar.get(tmpChar) < 0) {
                return false;
            }
        }
        return true;
    }
}
