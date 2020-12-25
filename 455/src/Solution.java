import java.util.Arrays;

class Solution {
    public int findContentChildren(int[] g, int[] s) {
        int res = 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int child = g.length-1;
        int bis = s.length-1;
        while (bis>=0&&child>=0){
            while (child>=0&&g[child]>s[bis]){
                child--;
            }
            if(child>=0){
                child--;
                bis--;
                res++;
            }
        }
        return res;
    }
}