import java.util.List;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        int maxLen = 0;
        for(int i=0;i<wordDict.size();i++){
            maxLen = Math.max(maxLen,wordDict.get(i).length());
        }
        for(int i=1;i<dp.length;i++){
            for(int j=i;j>=i-maxLen&&j>=0;j--){
                if(dp[j]&&wordDict.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}