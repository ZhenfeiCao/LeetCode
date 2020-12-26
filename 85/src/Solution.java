import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int res = 0;
        // 柱状图高度数组。 前后增加一个高度为0的 “哨兵”，避免判断栈空，简化代码
        int[] heights = new int[n + 2];
        // 对每i行及以上看成柱状图，求一次“柱状图中的最大矩形”
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 每一列的高度，要么为0，要么是上一行该列高度 + 1
                heights[j + 1] = matrix[i][j] == '0' ? 0 : 1 + heights[j + 1];
            }
            // 单调栈法求解 “柱状图最大矩形”
            Deque<Integer> stack = new ArrayDeque<>(m);
            stack.addLast(0);
            for (int j = 1; j < heights.length; j++) {
                while (heights[j] < heights[stack.peekLast()]) {
                    int h = heights[stack.pollLast()];
                    int w = j - stack.peekLast() - 1;
                    res = Math.max(res, h * w);
                }
                stack.addLast(j);
            }
        }
        return res;
    }
}