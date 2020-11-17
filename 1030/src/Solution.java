class Solution {
    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        int[][] res = new int[R*C][];
        int index = 0;
        int maxRow = 0;
        int maxCol = 0;
        maxRow = Math.max(r0,R-1-r0);
        maxCol = Math.max(c0,C-1-c0);
        for(int dis=0;dis<=maxRow+maxCol;dis++){
            int rowDis = dis<=maxCol?0:dis-maxCol;
            int colDis = dis-rowDis;
            for(;rowDis<=maxRow&&colDis>=0;rowDis++,colDis--){
                if(r0-rowDis>=0&&c0-colDis>=0){
                    res[index] = new int[]{r0-rowDis,c0-colDis};
                    index++;
                }
                if(colDis!=0&&r0-rowDis>=0&&c0+colDis<C){
                    res[index] = new int[]{r0-rowDis,c0+colDis};
                    index++;
                }
                if(rowDis!=0&&r0+rowDis<R&&c0-colDis>=0){
                    res[index] = new int[]{r0+rowDis,c0-colDis};
                    index++;
                }
                if(rowDis!=0&&colDis!=0&&r0+rowDis<R&&c0+colDis<C){
                    res[index] = new int[]{r0+rowDis,c0+colDis};
                    index++;
                }
            }
        }
        return res;
    }
}