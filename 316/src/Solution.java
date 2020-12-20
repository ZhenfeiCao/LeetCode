import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Solution {
    public String removeDuplicateLetters(String s) {
        int[] nums = new int[26];
        for(int i=0;i<s.length();i++){
            nums[s.charAt(i)-'a']++;
        }
        boolean[] hasCh = new boolean[26];
        StringBuffer sb = new StringBuffer("");
        for(int i=0;i<s.length();i++){
            char curCh = s.charAt(i);
            if(!hasCh[curCh-'a']){
                while (sb.length()>0&&sb.charAt(sb.length()-1)>curCh){
                    if(nums[sb.charAt(sb.length()-1)-'a']>0){
                        hasCh[sb.charAt(sb.length()-1)-'a'] = false;
                        sb.deleteCharAt(sb.length()-1);
                    }else{
                        break;
                    }
                }
                sb.append(curCh);
                hasCh[curCh-'a'] = true;
            }
            nums[curCh-'a']--;
        }
        return sb.toString();
    }
}