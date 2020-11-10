import java.util.*;

class Solution {

    Random rand = new Random();

    public int[][] kClosest(int[][] points, int K) {
        if(K==0)
            return null;
        findKCloest(0, points.length-1, points, K);
        return Arrays.copyOfRange(points, 0, K);
    }

    public void findKCloest(int left, int right, int[][] points, int K){
        int index = left + rand.nextInt(right - left + 1);
        int dis = points[index][0] * points[index][0] + points[index][1]*points[index][1];
        int i = left-1;
        swap(points, right, index);
        for(int j=left;j<right;j++){
            int tmpDis = points[j][0]*points[j][0] + points[j][1]*points[j][1];
            if(tmpDis<=dis){
                ++i;
                swap(points, j, i);
            }
        }
        i++;
        swap(points, i, right);

    }

    public void swap(int[][] points, int index1, int index2) {
        int[] temp = points[index1];
        points[index1] = points[index2];
        points[index2] = temp;
    }

}