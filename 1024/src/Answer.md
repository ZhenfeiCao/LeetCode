# 1024题解
考察点：动态规划or贪心

前言
本题要求从一系列视频子区间中选出尽可能少的一部分，使得这部分视频子区间能够重新剪出一个完整的视频。我们可以这样理解：给定区间 $[0,T)$的一系列子区间（可能重叠），要求从中选出尽可能少的子区间，使得这些子区间能够完全覆盖区间 $[0,T)$。

为下文表述方便，我们用 $[a,b)$ 来代表每一个子区间，第 $i$ 个子区间表示为 $[a_i,b_i)$。

#### 方法一：动态规划

思路及解法

比较容易想到的方法是动态规划，我们令 $dp[i]$ 表示将区间 $[0,i)$ 覆盖所需的最少子区间的数量。由于我们希望子区间的数目尽可能少，因此可以将所有 $dp[i]$ 的初始值设为一个大整数，并将 $dp[0]$（即空区间）的初始值设为 $0$。

我们可以枚举所有的子区间来依次计算出所有的 $dp$ 值。我们首先枚举 $i$，同时对于任意一个子区间 $[a_j,b_j)$，若其满足 $a_j < i \leq b_j$ ，那么它就可以覆盖区间 $[0,i)$ 的后半部分，而前半部分则可以用 $dp[a_j]$ 对应的最优方法进行覆盖，因此我们可以用 $dp[a_j] + 1$ 来更新 $dp[i]$。状态转移方程如下：

$dp[i] = \min \{ \textit{dp}[a_j] \} + 1 \quad (a_j < i \leq b_j)$

最终的答案即为 $dp[T]$。

```java
class Solution {
    public int videoStitching(int[][] clips, int T) {
        int[] dp = new int[T + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;
        for (int i = 1; i <= T; i++) {
            for (int[] clip : clips) {
                if (clip[0] < i && i <= clip[1]) {
                    dp[i] = Math.min(dp[i], dp[clip[0]] + 1);
                }
            }
        }
        return dp[T] == Integer.MAX_VALUE - 1 ? -1 : dp[T];
    }
}
```

#### 方法二：贪心

思路及解法

注意到对于所有左端点相同的子区间，其右端点越远越有利。且最佳方案中不可能出现两个左端点相同的子区间。于是我们预处理所有的子区间，对于每一个位置 $i$，我们记录以其为左端点的子区间中最远的右端点，记为 $maxn[i]$。

我们可以参考「55. 跳跃游戏的官方题解」，使用贪心解决这道题。

具体地，我们枚举每一个位置，假设当枚举到位置 $i$ 时，记左端点不大于 $i$ 的所有子区间的最远右端点为 $last$。这样 $last$ 就代表了当前能覆盖到的最远的右端点。

每次我们枚举到一个新位置，我们都用 $maxn[i]$ 来更新 $last$。如果更新后 $last==i$，那么说明下一个位置无法被覆盖，我们无法完成目标。

同时我们还需要记录上一个被使用的子区间的结束位置为 $pre$，每次我们越过一个被使用的子区间，就说明我们要启用一个新子区间，这个新子区间的结束位置即为当前的 $last$。也就是说，每次我们遇到 $i==pre$，则说明我们用完了一个被使用的子区间。这种情况下我们让答案加 $1$，并更新 $pre$ 即可。

```java
class Solution {
    public int videoStitching(int[][] clips, int T) {
        int[] maxn = new int[T];
        int last = 0, ret = 0, pre = 0;
        for (int[] clip : clips) {
            if (clip[0] < T) {
                maxn[clip[0]] = Math.max(maxn[clip[0]], clip[1]);
            }
        }
        for (int i = 0; i < T; i++) {
            last = Math.max(last, maxn[i]);
            if (i == last) {
                return -1;
            }
            if (i == pre) {
                ret++;
                pre = last;
            }
        }
        return ret;
    }
}
```

