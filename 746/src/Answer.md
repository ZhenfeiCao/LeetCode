# 746题解
考察点：使用最小花费爬楼梯

#### 方法一：动态规划

假设数组 $\textit{cost}$ 的长度为 $n$，则 $n$ 个阶梯分别对应下标 $0$ 到 $n−1$，楼层顶部对应下标 $n$，问题等价于计算达到下标 $n$ 的最小花费。可以通过动态规划求解。

创建长度为 $n+1$ 的数组 $\textit{dp}$，其中 $\textit{dp}[i]$ 表示达到下标 $i$ 的最小花费。

由于可以选择下标 $0$ 或 $1$ 作为初始阶梯，因此有 $\textit{dp}[0]=\textit{dp}[1]=0$。

当 $2 \le i \le n$ 时，可以从下标 $i-1$ 使用 $\textit{cost}[i-1]$ 的花费达到下标 $i$，或者从下标 $i-2$ 使用 $\textit{cost}[i-2]$ 的花费达到下标 $i$。为了使总花费最小，$\textit{dp}[i]$ 应取上述两项的最小值，因此状态转移方程如下：

$\textit{dp}[i]=\min(\textit{dp}[i-1]+\textit{cost}[i-1],\textit{dp}[i-2]+\textit{cost}[i-2])$

依次计算 $\textit{dp}$ 中的每一项的值，最终得到的 $\textit{dp}[n]$ 即为达到楼层顶部的最小花费。

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 0;
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[n];
    }
}
```

上述代码的时间复杂度和空间复杂度都是 $O(n)$。注意到当 $i \ge 2$ 时，$\textit{dp}[i]$ 只和 $\textit{dp}[i-1]$ 与 $\textit{dp}[i-2]$ 有关，因此可以使用滚动数组的思想，将空间复杂度优化到 $O(1)$。

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int prev = 0, curr = 0;
        for (int i = 2; i <= n; i++) {
            int next = Math.min(curr + cost[i - 1], prev + cost[i - 2]);
            prev = curr;
            curr = next;
        }
        return curr;
    }
}
```

