import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public String reorganizeString(String S) {
        int sLen = S.length();
        int[] chars = new int[26];
        char[] res = new char[sLen];
        Arrays.fill(res,'*');
        int maxPtr = 0;
        for(int i=0;i<S.length();i++){
            chars[S.charAt(i)-'a']++;
        }
        for(int i=1;i<26;i++){
            if(chars[i]>chars[maxPtr]) maxPtr = i;
        }
        if(chars[maxPtr]>(S.length()+1)/2){
            return "";
        }else{
            int len = 0,cur = 0, ptr = 0;
            while (chars[maxPtr]>0){
                res[cur] = (char)(maxPtr+'a');
                chars[maxPtr]--;
                cur = cur + 2;
                len++;
            }
            while (cur<sLen){
                cur--;
                while (chars[ptr]==0||ptr==maxPtr)
                    ptr++;
                while (cur<sLen&&chars[ptr]>0){
                    res[cur] = (char)(ptr+'a');
                    chars[ptr]--;
                    cur = cur + 2;
                    len++;
                }
            }
            cur = cur % sLen;
            if(cur == 0) cur =1;
            while (len!=S.length()){
                while (chars[ptr]==0||ptr==maxPtr)
                    ptr++;
                while (chars[ptr]>0){
                    while (res[cur]!='*'){
                        cur = (cur+1)%sLen;
                    }
                    res[cur] = (char)(ptr+'a');
                    chars[ptr]--;
                    len++;
                }
            }
            return new String(res);
        }
    }

}
