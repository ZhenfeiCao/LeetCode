# 493题解
考察点：翻转对

前言
本题与「327. 区间和的个数」非常相像。

在 327 题中，我们要对前缀和数组的每一个元素 $\textit{preSum}[i]$，找出所有位于 $i$ 左侧的下标 $j$ 的数量，要求 $j$ 满足 $\textit{preSum}[j] \in [\textit{preSum}[i]-\textit{upper}, \textit{preSum}[i]-\textit{lower}]$。而在此题中，我们则要对数组中的每一个元素 $\textit{sum}[i]$，找出位于 $i$ 左侧，且满足 $\textit{nums}[j] > 2\cdot \textit{nums}[i]$ 的下标 $j$。

不难发现，二者都是要对数组中的每一个元素，统计「在它左侧，且取值位于某个区间」的元素数量。两个问题唯一的区别仅仅在于取值区间的不同，因此可以用相似的方法解决这两个问题。

在「327 题的题解：区间和的个数」中，我们介绍了归并排序、线段树、树状数组以及平衡搜索树等多种解法。对于本题，我们只给出基于归并排序与树状数组的方法，感兴趣的读者可以参照前面给出的链接，自行完成其他方法的代码。

#### 方法一：归并排序

思路及解法

在归并排序的过程中，假设对于数组 $\textit{nums}[l..r]$ 而言，我们已经分别求出了子数组 $\textit{nums}[l..m]$ 与 $\textit{nums}[m+1..r]$ 的翻转对数目，并已将两个子数组分别排好序，则 $\textit{nums}[l..r]$ 中的翻转对数目，就等于两个子数组的翻转对数目之和，加上左右端点分别位于两个子数组的翻转对数目。

我们可以为两个数组分别维护指针 $i,j$。对于任意给定的 $i$ 而言，我们不断地向右移动 $j$，直到 $\textit{nums}[i] \le 2\cdot \textit{nums}[j]$。此时，意味着以 $i$ 为左端点的翻转对数量为 $j-m-1$。随后，我们再将 $i$ 向右移动一个单位，并用相同的方式计算以 $i$ 为左端点的翻转对数量。不断重复这样的过程，就能够求出所有左右端点分别位于两个子数组的翻转对数目。

```java
class Solution {
    public int reversePairs(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        return reversePairsRecursive(nums, 0, nums.length - 1);
    }

    public int reversePairsRecursive(int[] nums, int left, int right) {
        if (left == right) {
            return 0;
        } else {
            int mid = (left + right) / 2;
            int n1 = reversePairsRecursive(nums, left, mid);
            int n2 = reversePairsRecursive(nums, mid + 1, right);
            int ret = n1 + n2;

            // 首先统计下标对的数量
            int i = left;
            int j = mid + 1;
            while (i <= mid) {
                while (j <= right && (long) nums[i] > 2 * (long) nums[j]) {
                    j++;
                }
                ret += j - mid - 1;
                i++;
            }

            // 随后合并两个排序数组
            int[] sorted = new int[right - left + 1];
            int p1 = left, p2 = mid + 1;
            int p = 0;
            while (p1 <= mid || p2 <= right) {
                if (p1 > mid) {
                    sorted[p++] = nums[p2++];
                } else if (p2 > right) {
                    sorted[p++] = nums[p1++];
                } else {
                    if (nums[p1] < nums[p2]) {
                        sorted[p++] = nums[p1++];
                    } else {
                        sorted[p++] = nums[p2++];
                    }
                }
            }
            for (int k = 0; k < sorted.length; k++) {
                nums[left + k] = sorted[k];
            }
            return ret;
        }
    }
}
```

