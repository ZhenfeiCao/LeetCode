import java.util.Arrays;

class Solution {
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        for(int max=A.length-1;max>=2;max--){
            if(A[max-2]+A[max-1]>A[max])
                return A[max-2]+A[max-1]+A[max];
        }
        return 0;
    }
}
