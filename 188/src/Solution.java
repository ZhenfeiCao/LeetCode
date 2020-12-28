class Solution {
    public int maxProfit(int k, int[] prices) {
        if(prices.length==0)
            return 0;
        int[] dp = new int[k*2+1];
        for(int i=1;i<2*k+1;i=i+2){
            dp[i] = -prices[0];
        }
        for(int i=0;i<prices.length;i++){
            for(int j=1;j<=k*2;j++){
                int isBuy = j%2==0?1:-1;
                dp[j] = Math.max(dp[j],dp[j-1]+isBuy*prices[i]);
            }
        }
        int max = 0;
        for(int i=0;i<2*k+1;i=i+2){
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}