import java.util.HashSet;
import java.util.Set;

class Solution {

    int result = 0;

    public int totalNQueens(int n) {
        dfs(0, n, 0,0,0);
        return result;
    }

    public void dfs(int row, int n,int columns, int diagonals1, int diagonals2){
        if(row == n){
            result ++;
            return;
        }
        int availablePositions = ((1 << n) - 1) & (~(columns | diagonals1 | diagonals2));
        while (availablePositions != 0) {
            int position = availablePositions & (-availablePositions);
            availablePositions = availablePositions & (availablePositions - 1);
            dfs(row + 1, n, columns | position, (diagonals1 | position) << 1, (diagonals2 | position) >> 1);
        }
    }

}
