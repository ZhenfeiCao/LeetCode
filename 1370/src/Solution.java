import java.util.HashMap;

class Solution {
    public String sortString(String s) {
        StringBuffer res = new StringBuffer("");
        int[] chars = new int[26];
        for(int i=0;i<s.length();i++){
            chars[s.charAt(i)- 'a' ]++;
        }
        int ptr = 0;
        for(;ptr<chars.length&&chars[ptr]==0;ptr++);
        while (ptr<chars.length&&chars[ptr]!=0){
            for(;ptr<chars.length;ptr++){
                if(chars[ptr]>0){
                    res.append((char) (ptr + 'a'));
                    chars[ptr]--;
                }
            }
            for(ptr=chars.length-1;ptr>=0&&chars[ptr]==0;ptr--);
            for(; ptr>=0; ptr--){
                if(chars[ptr]>0){
                    res.append((char) (ptr + 'a'));
                    chars[ptr]--;
                }
            }
            for(ptr=0;ptr<chars.length&&chars[ptr]==0;ptr++);
        }
        return res.toString();
    }

}
