import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int findMinArrowShots(int[][] points) {
        if(points.length<=1)
            return points.length;
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]<o2[0]){
                    return -1;
                }else if (o1[0]>o2[0]){
                    return 1;
                }else{
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });
        int res = 0, ptr = 0;
        int[] common;
        while (ptr<points.length){
            common = points[ptr];
            ptr++;
            while (ptr<points.length&&points[ptr][0]<=common[1]){
                common[0] = points[ptr][0];
                common[1] = Math.min(points[ptr][1],common[1]);
                ptr++;
            }
            res++;
        }
        return res;
    }
}
