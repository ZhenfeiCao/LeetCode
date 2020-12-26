# 85题解
考察点：最大矩形

#### 方法一：单调栈

如果我们把矩阵看作：以第三行为底边，每一列从下往上连续的1的个数作为高度的柱状图，如图所示：

![https://pic.leetcode-cn.com/1608951249-DSFarc-image.png](https://pic.leetcode-cn.com/1608951249-DSFarc-image.png)

问题就转化成了 84. 柱状图中最大的矩形

直接套用那一题的解法，就可以以O(n)的复杂度算出该柱状图中最大的矩形的面积是 6

但是这例子中只是特别选取了第3行作为底边，事实上选择哪一行作为底边是不确定的。

因此这道题我们需要对矩阵中以每一行作为底边的柱状图都求解一次最大矩形面积，再取最大值作为答案即可。

```java
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
```
