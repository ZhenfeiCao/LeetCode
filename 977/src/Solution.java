class Solution {
    public int[] sortedSquares(int[] A) {
        int[] squares = new int[A.length];
        int neg = -1;
        for(int i=0;i<A.length;i++){
            if(A[i]<0){
                neg++;
            }else{
                break;
            }
        }
        int negPlus1 = neg+1;
        int pos = 0;
        while(neg>=0 || negPlus1<A.length){
            if(neg>=0 && negPlus1 < A.length){
                if(A[neg]*A[neg]>A[negPlus1]*A[negPlus1]){
                    squares[pos] = A[negPlus1]*A[negPlus1];
                    negPlus1++;
                }else{
                    squares[pos] = A[neg]*A[neg];
                    neg--;
                }
            }else if(neg<0){
                squares[pos] = A[negPlus1]*A[negPlus1];
                negPlus1++;
            }else{
                squares[pos] = A[neg]*A[neg];
                neg--;
            }
            pos++;
        }
        return squares;
    }
}
