class Solution {
    public int[] sortArrayByParityII(int[] A) {
        int len = A.length;
        int oddIndex = 1;
        int evenIndex = 0;
        for(evenIndex=0;evenIndex<len;evenIndex+=2){
            if(A[evenIndex]%2!=0){
                while (A[oddIndex]%2!=0){
                    oddIndex+=2;
                }
                swap(A,evenIndex,oddIndex);
            }
        }
        return A;
    }

    public void swap(int[] A, int i, int j){
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }
}
