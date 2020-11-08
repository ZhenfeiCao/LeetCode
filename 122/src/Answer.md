# 122题解
考察点：买卖股票的最佳时机 II

#### 方法一：动态规划

考虑到「不能同时参与多笔交易」，因此每天交易结束后只可能存在手里有一支股票或者没有股票的状态。

定义状态 $\textit{dp}[i][0]$表示第 $i$ 天交易完后手里没有股票的最大利润，$\textit{dp}[i][1]$ 表示第 $i$ 天交易完后手里持有一支股票的最大利润（$i$ 从 $0$ 开始）。

考虑 $\textit{dp}[i][0]$ 的转移方程，如果这一天交易完后手里没有股票，那么可能的转移状态为前一天已经没有股票，即 $\textit{dp}[i-1][0]$，或者前一天结束的时候手里持有一支股票，即 $\textit{dp}[i-1][1]$，这时候我们要将其卖出，并获得 $\textit{prices}[i]$ 的收益。因此为了收益最大化，我们列出如下的转移方程：

$\textit{dp}[i][0]=\max\{\textit{dp}[i-1][0],\textit{dp}[i-1][1]+\textit{prices}[i]\}$

再来考虑 $\textit{dp}[i][1]$，按照同样的方式考虑转移状态，那么可能的转移状态为前一天已经持有一支股票，即 $\textit{dp}[i-1][1]$，或者前一天结束时还没有股票，即 $\textit{dp}[i-1][0]$，这时候我们要将其买入，并减少 $\textit{prices}[i]$ 的收益。可以列出如下的转移方程：

$\textit{dp}[i][1]=\max\{\textit{dp}[i-1][1],\textit{dp}[i-1][0]-\textit{prices}[i]\}$

对于初始状态，根据状态定义我们可以知道第 $0$ 天交易结束的时候 $\textit{dp}[0][0]=0$，$\textit{dp}[0][1]=-\textit{prices}[0]$。

因此，我们只要从前往后依次计算状态即可。由于全部交易结束后，持有股票的收益一定低于不持有股票的收益，因此这时候 $\textit{dp}[n-1][0]$ 的收益必然是大于 $\textit{dp}[n-1][1]$ 的，最后的答案即为 $\textit{dp}[n-1][0]$。

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }
}
```

注意到上面的状态转移方程中，每一天的状态只与前一天的状态有关，而与更早的状态都无关，因此我们不必存储这些无关的状态，只需要将 $\textit{dp}[i-1][0]$ 和 $\textit{dp}[i-1][1]$ 存放在两个变量中，通过它们计算出 $\textit{dp}[i][0]$和 $\textit{dp}[i][1]$ 并存回对应的变量，以便于第 $i+1$ 天的状态转移即可。

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int dp0 = 0, dp1 = -prices[0];
        for (int i = 1; i < n; ++i) {
            int newDp0 = Math.max(dp0, dp1 + prices[i]);
            int newDp1 = Math.max(dp1, dp0 - prices[i]);
            dp0 = newDp0;
            dp1 = newDp1;
        }
        return dp0;
    }
}
```

#### 方法二：贪心

由于股票的购买没有限制，因此整个问题等价于寻找 $x$ 个**不相交**的区间 $(l_i,r_i]$ 使得如下的等式最大化。

$\sum_{i=1}^{x} a[r_i]-a[l_i]$

其中 $l_i$表示在第 $l_i$天买入，$r_i$表示在第 $r_i$天卖出。

同时我们注意到对于 $(l_i,r_i]$ 这一个区间贡献的价值 $a[r_i]-a[l_i]$，其实等价于 $(l_i,l_i+1],(l_i+1,l_i+2],\ldots,(r_i-1,r_i]$这若干个区间长度为 $1$ 的区间的价值和，即

$a[r_i]−a[l_i]=(a[r_i]−a[r_i−1])+(a[r_i−1]−a[r_i−2])+…+(a[l_i+1]−a[l_i])$

因此问题可以简化为找 $x$ 个长度为 $1$ 的区间 $(l_i,l_i+1]$使得 $\sum_{i=1}^{x} a[l_i+1]-a[l_i]$价值最大化。

贪心的角度考虑我们每次选择贡献大于 $0$ 的区间即能使得答案最大化，因此最后答案为

$\textit{ans}=\sum_{i=1}^{n-1}\max\{0,a[i]-a[i-1]\}$其中 $n$ 为数组的长度。

需要说明的是，贪心算法只能用于计算最大利润，计算的过程并不是实际的交易过程。

考虑题目中的例子 $[1,2,3,4,5]$，数组的长度 $n=5$，由于对所有的 $1 \le i < n$ 都有 $a[i]>a[i-1]$，因此答案为

$\textit{ans}=\sum_{i=1}^{n-1}a[i]-a[i-1]=4$
但是实际的交易过程并不是进行 $4$ 次买入和 $4$ 次卖出，而是在第 $1$ 天买入，第 $5$ 天卖出。

```java
class Solution {
    public int maxProfit(int[] prices) {
        int ans = 0;
        int n = prices.length;
        for (int i = 1; i < n; ++i) {
            ans += Math.max(0, prices[i] - prices[i - 1]);
        }
        return ans;
    }
}
```

