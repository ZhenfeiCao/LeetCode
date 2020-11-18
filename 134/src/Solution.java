class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        for(int i=0;i<len;){
            int sum = 0;
            int beginIndex = i;
            while (sum>=0&&i-beginIndex<len){
                sum+=gas[i%len]-cost[i%len];
                i++;
            }
            if(i-beginIndex==len&&sum>=0){
                return beginIndex;
            }
        }
        return -1;
    }
}