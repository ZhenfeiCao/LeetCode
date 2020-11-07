import java.util.ArrayList;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> tmpInterval = new ArrayList<>();
        int index = 0;
        int interLeft = newInterval[0];
        int interRight = newInterval[1];
        while (index<intervals.length&&intervals[index][1]< interLeft){
            tmpInterval.add(intervals[index]);
            index++;
        }
        if(index==intervals.length||intervals[index][0]>interRight){
            tmpInterval.add(newInterval);
        }else{
            while (index<intervals.length&&!(intervals[index][0]>interRight)){
                interLeft = Math.min(intervals[index][0], interLeft);
                interRight = Math.max(intervals[index][1], interRight);
                index++;
            }
            tmpInterval.add(new int[]{interLeft, interRight});
        }
        while (index<intervals.length&&intervals[index][0]>interRight){
            tmpInterval.add(intervals[index]);
            index++;
        }
        int[][] result = new int[tmpInterval.size()][];
        for(int i=0;i<tmpInterval.size();i++){
            result[i] = tmpInterval.get(i);
        }
        return result;
    }
}