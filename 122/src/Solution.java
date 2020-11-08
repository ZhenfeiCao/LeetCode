class Solution {
    public int maxProfit(int[] prices) {
        int[] res = new int[prices.length];
        res[0] = 0;
        for(int i=1;i<prices.length;i++){
            res[i] = Math.max(res[i-1], res[i-1]+prices[i]-prices[i-1]);
        }
        return res[res.length-1];
    }
}