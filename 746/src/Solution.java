class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int[] minCost = new int[cost.length+1];
        for(int i=0;i<cost.length+1;i++){
            int lastTwo = i-2<0?0:minCost[i-2];
            int last = i-1<0?0:minCost[i-1];
            int curCost = i==cost.length?0:cost[i];
            minCost[i] = Math.min(last, lastTwo) + curCost;
        }
        return minCost[minCost.length-1];
    }
}