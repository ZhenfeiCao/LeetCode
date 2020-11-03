# 941题解
考察点：有效的山脉数组

#### 方法一：线性扫描

按题意模拟即可。我们从数组的最左侧开始向右扫描，直到找到第一个不满足 $A[i] < A[i + 1]$ 的下标 $i$，那么 $i$ 就是这个数组的最高点的下标。如果 $i=0$ 或者不存在这样的 $i$（即整个数组都是单调递增的），那么就返回 $\text{false}$。否则从 $i$ 开始继续向右扫描，判断接下来的的下标 $j$ 是否都满足 $A[j] > A[j + 1]$，若都满足就返回 $\text{true}$，否则返回 $\text{false}$。

```java
class Solution {
    public boolean validMountainArray(int[] A) {
        int N = A.length;
        int i = 0;

        // 递增扫描
        while (i + 1 < N && A[i] < A[i + 1]) {
            i++;
        }

        // 最高点不能是数组的第一个位置或最后一个位置
        if (i == 0 || i == N - 1) {
            return false;
        }

        // 递减扫描
        while (i + 1 < N && A[i] > A[i + 1]) {
            i++;
        }

        return i == N - 1;
    }
}
```

#### 方法二：双指针

可以使用两种指针，一个从左边找最高山峰，一个从右边找最高山峰，最后判断找到的是不是同一个山峰

![](https://pic.leetcode-cn.com/1604367864-BSFQoM-image.png)

```java
class Solution {
        public boolean validMountainArray(int[] A) {
        int len = A.length;
        int left = 0;
        int right = len - 1;
        //从左边往右边找，一直找到山峰为止
        while (left + 1 < len && A[left] < A[left + 1])
            left++;
        //从右边往左边找，一直找到山峰为止
        while (right > 0 && A[right - 1] > A[right])
            right--;
        //判断从左边和从右边找的山峰是不是同一个
        return left > 0 && right < len - 1 && left == right;
    }
}
```

