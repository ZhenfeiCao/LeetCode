# 416题解
考察点：分割等和子集

#### 方法一：动态规划

思路与算法

这道题可以换一种表述：给定一个只包含正整数的非空数组$nums[0]$，判断是否可以从数组中选出一些数字，使得这些数字的和等于整个数组的元素和的一半。因此这个问题可以转换成「0-10−1 背包问题」。这道题与传统的「0-10−1 背包问题」的区别在于，传统的「0-10−1 背包问题」要求选取的物品的重量之和不能超过背包的总容量，这道题则要求选取的数字的和恰好等于整个数组的元素和的一半。类似于传统的「0-10−1 背包问题」，可以使用动态规划求解。

在使用动态规划求解之前，首先需要进行以下判断。

- 根据数组的长度 n 判断数组是否可以被划分。如果 n<2，则不可能将数组分割成元素和相等的两个子集，因此直接返回 $false$。

- 计算整个数组的元素和 $sum$ 以及最大元素 $maxNum$。如果 $sum$ 是奇数，则不可能将数组分割成元素和相等的两个子集，因此直接返回 $false$。如果 $sum$ 是偶数，则令 $target=\frac{sum}{2}$，需要判断是否可以从数组中选出一些数字，使得这些数字的和等于 $target$。如果 $maxNum>target$，则除了$maxNum$ 以外的所有元素之和一定小于 $target$，因此不可能将数组分割成元素和相等的两个子集，直接返回 $false$。


创建二维数组 $dp$，包含 n 行 $target+1$ 列，其中 $dp[i][j]$表示从数组的$ [0,i] $下标范围内选取若干个正整数（可以是 0 个），是否存在一种选取方案使得被选取的正整数的和等于 j。初始时，$dp$ 中的全部元素都是 $false$。

在定义状态之后，需要考虑边界情况。以下两种情况都属于边界情况。

- 如果不选取任何正整数，则被选取的正整数等于 0。因此对于所有 $0≤i<n$，都有 $dp[i][0]=true$。

- 当 i==0 时，只有一个正整数 $nums[0]$ 可以被选取，因此 $dp[0][nums[0]]=true$。


对于 i>0 且 j>0 的情况，如何确定 $dp[i][j]$ 的值？需要分别考虑以下两种情况。

- 如果 $j≥nums[i]$，则对于当前的数字 $nums[i]$，可以选取也可以不选取，两种情况只要有一个为 $true$，就有 $dp[i][j]=true$。如果不选取 $nums[i]$，则 $dp[i][j]=dp[i−1][j]$；如果选取 $nums[i]$，则 $dp[i][j]=dp[i−1][j−nums[i]]$。
- 如果 $j<nums[i]​$，则在选取的数字的和等于 j 的情况下无法选取当前的数字 $nums[i]​$，因此有 $dp[i][j]=dp[i−1][j]​$。

状态转移方程如下：

$$ dp[i][j]=\left\{
\begin{array}{rcl}
dp[i-1][j] | dp[i-1][j-nums[i]]      &      & j>=nums[i]\\
dp[i-1][j]     &      & j<nums[i]\\
\end{array} \right. $$	


最终得到 $dp[n−1][target]$即为答案。

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        if (maxNum > target) {
            return false;
        }
        boolean[][] dp = new boolean[n][target + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;
        for (int i = 1; i < n; i++) {
            int num = nums[i];
            for (int j = 1; j <= target; j++) {
                if (j >= num) {
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - num];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][target];
    }
}
```
