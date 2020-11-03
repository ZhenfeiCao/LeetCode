class Solution {
    public boolean validMountainArray(int[] A) {
        if(A.length<3)
            return false;
        int top = 0;
        while (top+1 < A.length && A[top] < A[top+1]) {
            top++;
        }
        if(top==0||top+1==A.length){
            return false;
        }
        for(int i=top;i+1<A.length;i++){
            if (A[i] <= A[i+1]){
                return false;
            }
        }
        return true;
    }
}
