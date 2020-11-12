# 922题解
考察点：按奇偶排序数组 II

#### 方法一：两次遍历

思路和算法

遍历一遍数组把所有的偶数放进 ans[0]，ans[2]，ans[4]，依次类推。

再遍历一遍数组把所有的奇数依次放进 ans[1]，ans[3]，ans[5]，依次类推。

```java
class Solution {
    public int[] sortArrayByParityII(int[] A) {
        int n = A.length;
        int[] ans = new int[n];

        int i = 0;
        for (int x : A) {
            if (x % 2 == 0) {
                ans[i] = x;
                i += 2;
            }
        }
        i = 1;
        for (int x : A) {
            if (x % 2 == 1) {
                ans[i] = x;
                i += 2;
            }
        }
        return ans;
    }
}
```

#### 方法二：双指针

如果原数组可以修改，则可以使用就地算法求解。

为数组的偶数下标部分和奇数下标部分分别维护指针 $i, j$。随后，在每一步中，如果 $A[i]$ 为奇数，则不断地向前移动 $j$（每次移动两个单位），直到遇见下一个偶数。此时，可以直接将 $A[i]$ 与 $A[j]$ 交换。我们不断进行这样的过程，最终能够将所有的整数放在正确的位置上。

```java
class Solution {
    public int[] sortArrayByParityII(int[] A) {
        int n = A.length;
        int j = 1;
        for (int i = 0; i < n; i += 2) {
            if (A[i] % 2 == 1) {
                while (A[j] % 2 == 1) {
                    j += 2;
                }
                swap(A, i, j);
            }
        }   
        return A;
    }

    public void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}
```

