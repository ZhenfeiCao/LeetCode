import java.util.Arrays;

class Solution {
    public int findCircleNum(int[][] isConnected) {
        int res = 0;
        int num = isConnected.length;
        int ptr = 0;
        boolean[] visited = new boolean[num];
        Arrays.fill(visited, false);
        while (ptr<num){
            if(!visited[ptr]){
                visited[ptr] = true;
                dfs(isConnected, ptr, visited);
                res++;
            }
            ptr++;
        }
        return res;
    }

    public void dfs(int[][] isConnected, int ptr, boolean[] visited){
        for(int i=0;i<isConnected.length;i++){
            if(isConnected[ptr][i]==1&&!visited[i]){
                visited[i] = true;
                dfs(isConnected, i, visited);
            }
        }
    }

}