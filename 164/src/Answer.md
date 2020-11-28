# 164题解
考察点：最大间距

#### 方法一：基数排序

思路与算法

一种最简单的思路是将数组排序后再找出最大间距，但传统的基于比较的排序算法（快速排序、归并排序等）均需要 $O(NlogN)$ 的时间复杂度。我们必须使用其他的排序算法。例如，基数排序可以在 $O(N)$ 的时间内完成整数之间的排序。

```java
class Solution {
    public int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        long exp = 1;
        int[] buf = new int[n];
        int maxVal = Arrays.stream(nums).max().getAsInt();

        while (maxVal >= exp) {
            int[] cnt = new int[10];
            for (int i = 0; i < n; i++) {
                int digit = (nums[i] / (int) exp) % 10;
                cnt[digit]++;
            }
            for (int i = 1; i < 10; i++) {
                cnt[i] += cnt[i - 1];
            }
            for (int i = n - 1; i >= 0; i--) {
                int digit = (nums[i] / (int) exp) % 10;
                buf[cnt[digit] - 1] = nums[i];
                cnt[digit]--;
            }
            System.arraycopy(buf, 0, nums, 0, n);
            exp *= 10;
        }

        int ret = 0;
        for (int i = 1; i < n; i++) {
            ret = Math.max(ret, nums[i] - nums[i - 1]);
        }
        return ret;
    }
}
```

#### 方法二：基于桶的算法

思路与算法

设长度为 $N$ 的数组中最大值为 $\textit{max,min}$，则不难发现相邻数字的最大间距不会小于 $\lceil (\textit{max}-\textit{min}) / (N-1) \rceil$。

为了说明这一点，我们使用反证法：假设相邻数字的间距都小于 $\lceil (\textit{max}-\textit{min}) / (N-1) \rceil$，并记数组排序后从小到大的数字依次为 $A_1, A_2, ..., A_N$，则有

$\begin{aligned} A_N - A_1&=(A_N - A_{N-1})+(A_{N-1}-A_{N-2})+ ... + (A_2 - A_1) \\ &< \lceil (\textit{max}-\textit{min}) / (N-1) \rceil + \lceil (\textit{max}-\textit{min}) / (N-1) \rceil + ... + \lceil (\textit{max}-\textit{min}) / (N-1) \rceil \\ &< (N-1) \cdot \lceil (\textit{max}-\textit{min}) / (N-1) \rceil= \textit{max}-\textit{min} \end{aligned}$
但根据 $A_1, A_N$的定义，一定有 $A_1=\textit{min}$，且 $A_N=\textit{max}$，故上式会导出矛盾。

因此，我们可以选取整数 $d = \lfloor (\textit{max}-\textit{min}) / (N-1) \rfloor < \lceil (\textit{max}-\textit{min}) / (N-1) \rceil$。随后，我们将整个区间划分为若干个大小为 $d$ 的桶，并找出每个整数所在的桶。根据前面的结论，能够知道，元素之间的最大间距一定不会出现在某个桶的内部，而一定会出现在不同桶当中。

因此，在找出每个元素所在的桶之后，我们可以维护每个桶内元素的最大值与最小值。随后，只需从前到后不断比较相邻的桶，用后一个桶的最小值与前一个桶的最大值之差作为两个桶的间距，最终就能得到所求的答案。

```java
class Solution {
    public int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        int minVal = Arrays.stream(nums).min().getAsInt();
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int d = Math.max(1, (maxVal - minVal) / (n - 1));
        int bucketSize = (maxVal - minVal) / d + 1;

        int[][] bucket = new int[bucketSize][2];
        for (int i = 0; i < bucketSize; ++i) {
            Arrays.fill(bucket[i], -1); // 存储 (桶内最小值，桶内最大值) 对， (-1, -1) 表示该桶是空的
        }
        for (int i = 0; i < n; i++) {
            int idx = (nums[i] - minVal) / d;
            if (bucket[idx][0] == -1) {
                bucket[idx][0] = bucket[idx][1] = nums[i];
            } else {
                bucket[idx][0] = Math.min(bucket[idx][0], nums[i]);
                bucket[idx][1] = Math.max(bucket[idx][1], nums[i]);
            }
        }

        int ret = 0;
        int prev = -1;
        for (int i = 0; i < bucketSize; i++) {
            if (bucket[i][0] == -1) {
                continue;
            }
            if (prev != -1) {
                ret = Math.max(ret, bucket[i][0] - bucket[prev][1]);
            }
            prev = i;
        }
        return ret;
    }
}
```

