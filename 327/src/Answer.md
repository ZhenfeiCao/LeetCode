# 327题解
考察点：区间和的个数

#### 方法一：归并排序

思路与算法

设前缀和数组为 $\textit{preSum}$，则问题等价于求所有的下标对 $(i,j)$，满足

$\textit{preSum}[j] - \textit{preSum}[i] \in [\textit{lower}, \textit{upper}]$

我们先考虑如下的问题：给定两个升序排列的数组 $n_1$, $n_2$，试找出所有的下标对 $(i,j)$，满足

$n_2[j] - n_1[i] \in [\textit{lower}, \textit{upper}]$

在已知两个数组均为升序的情况下，这一问题是相对简单的：我们在 $n_2$中维护两个指针 $l,r$。起初，它们都指向 $n_2$的起始位置。

随后，我们考察 $n_1$的第一个元素。首先，不断地将指针 $l$ 向右移动，直到 $n_2[l] \ge n_1[0] + \textit{lower}$ 为止，此时， $l $及其右边的元素均大于或等于 $n_1[0] + \textit{lower}$；随后，再不断地将指针 $r$ 向右移动，直到 $n_2[r] > n_1[0] + \textit{upper}$ 为止，则 $r$ 左边的元素均小于或等于 $n_1[0] + \textit{upper}$。故区间 $[l,r)$ 中的所有下标 $j$，都满足

$n_2[j] - n_1[0] \in [\textit{lower}, \textit{upper}]$

接下来，我们考察 $n_1$ 的第二个元素。由于 $n_1$ 是递增的，不难发现 $l,r$ 只可能向右移动。因此，我们不断地进行上述过程，并对于 $n_1$ 中的每一个下标，都记录相应的区间 $[l,r)$ 的大小。最终，我们就统计得到了满足条件的下标对 $(i,j)$ 的数量。

**对归并排序代码的解释**：

*初始归并*：
此时只会有0或1个元素，不涉及左右两段的情况，是可以的
*合并归并*：
此时是有左右两段的，左右两段是分别有序的，对前缀和数组排序并不会修改数组中元素的值，只是改变了元素的位置，如对left\~right=3\~5位置的前缀和排序，排序后前缀和3\~5位置的数还是原来3\~5位置的数，只是排列变化了

设想一个一般的情况，现在是某一层的递归，左，右两段区间left\~mid, mid+1\~right的符合要求的区间数量已经通过countRangeSumRecursive计算了出来，整个left\~right区间中可能的符合要求的区间情况是两端点在left\~mid中；两端点在mid+1\~right；一个端点在left\~mid中，一个端点在mid+1\~right中，所以现在只要求出第三种情况的区间数量就可以了
通过上面的说明，left\~mid,mid+1\~right区间中的数还是原来区间中的数，只是顺序变成了有序，而有序是容易计算符合要求的区间数量的，一个图说明为什么第三种情况排序前后符合数量的区间数量是不变的。

1. 总的区间中满足情况的个数等于两个端点都在左部的情况+两个端点都在右部的情况+一个端点在左部一个在右部的情况
2. 一个端点在左部一个在右部排序前后计算结果不变

![](https://pic.leetcode-cn.com/1604740489-tzFzdh-image.png)

```java
public class Solution {

    public int countRangeSumMergSort(int[] nums, int lower, int upper) {
        int n = nums.length;

        if (n == 0) {
            return 0;
        }

        long[] sum = new long[n + 1];
        long s = 0;

        for (int i = 0; i < n; i++) {
            s += nums[i];
            sum[i + 1] = s;
        }

        return countRangeSumMergeSortRecursize(sum, 0, n, lower, upper);
    }

    int countRangeSumMergeSortRecursize(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return 0;
        }

        int middle = (left + right) / 2;
        int result = 0;

        result += countRangeSumMergeSortRecursize(sum, left, middle, lower, upper);
        result += countRangeSumMergeSortRecursize(sum, middle + 1, right, lower, upper);

        int i = left;
        int l = middle + 1;
        int r = middle + 1;

        while (i <= middle) {
            while ((l <= right) && (sum[l] - sum[i] < lower)) {
                l++;
            }

            while ((r <= right) && (sum[r] - sum[i] <= upper)) {
                r++;
            }

            result += (r - l);
            i++;
        }

        int[] sort = new int[right - left + 1];
        int p = 0;
        int p1 = left;
        int p2 = middle + 1;

        while ((p1 <= middle) || (p2 <= right)) {
            if (p1 > middle) {
                sort[p++] = (int) sum[p2++];
            } else if (p2 > right) {
                sort[p++] = (int) sum[p1++];
            } else {
                if (sum[p1] < sum[p2]) {
                    sort[p++] = (int) sum[p1++];
                } else {
                    sort[p++] = (int) sum[p2++];
                }
            }
        }

        for (int j = 0; j < right - left + 1; j++) {
            sum[left + j] = sort[j];
        }

        return result;
    }
}
```
