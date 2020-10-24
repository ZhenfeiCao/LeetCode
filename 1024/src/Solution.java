import java.util.ArrayList;
import java.util.Arrays;

class Solution {

    public int videoStitching(int[][] clips, int T) {
        int[] dp = new int[T+1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;
        for(int i=1;i<T+1;i++){
            for(int j=0;j<clips.length;j++){
                if(clips[j][0]<i&&i<=clips[j][1]){
                    dp[i] = Math.min(dp[i],dp[clips[j][0]]+1);
                }
            }
        }
        return dp[T]==Integer.MAX_VALUE-1?-1:dp[T];
    }

}
