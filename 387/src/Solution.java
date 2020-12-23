import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int firstUniqChar(String s) {
        int[] charIndex = new int[26];
        Arrays.fill(charIndex,s.length());
        for(int i=0;i<s.length();i++){
            if(charIndex[s.charAt(i)-'a']==s.length()){
                charIndex[s.charAt(i)-'a'] = i;
            }else if(charIndex[s.charAt(i)-'a']==-1){
                continue;
            }else{
                charIndex[s.charAt(i)-'a'] = -1;
            }
        }
        int res = s.length();
        for(int i=0;i<26;i++){
            if(charIndex[i]>=0&&charIndex[i]<s.length()){
                res = Math.min(res, charIndex[i]);
            }
        }
        return (res>=0&&res<s.length())?res:-1;
    }
}