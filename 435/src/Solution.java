import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int res = 0, left = Integer.MIN_VALUE, ptr = 0;
        while (ptr< intervals.length){
            while (ptr<intervals.length&&intervals[ptr][0]<left){
                ptr++;
                res++;
            }
            if(ptr<intervals.length) {
                left = intervals[ptr][1];
                ptr++;
            }
        }
        return res;
    }
}