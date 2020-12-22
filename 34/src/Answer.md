# 34题解
考察点：在排序数组中查找元素的第一个和最后一个位置

#### 方法一：二分查找

思路及解法

直观的思路肯定是从前往后遍历一遍。用两个变量记录第一次和最后一次遇见 $\textit{target}$ 的下标，但这个方法的时间复杂度为 $O(n)$，没有利用到数组升序排列的条件。

由于数组已经排序，因此整个数组是单调递增的，我们可以利用二分法来加速查找的过程。

考虑 $\textit{target}$ 开始和结束位置，其实我们要找的就是数组中「第一个等于 $\textit{target}$ 的位置」（记为 $\textit{leftIdx}$）和「第一个大于 $\textit{target}$ 的位置减一」（记为 $\textit{rightIdx}$）。

二分查找中，寻找 $\textit{leftIdx}$ 即为在数组中寻找第一个大于等于 $\textit{target}$ 的下标，寻找 $\textit{rightIdx}$ 即为在数组中寻找第一个大于 $\textit{target}$ 的下标，然后将下标减一。两者的判断条件不同，为了代码的复用，我们定义 binarySearch(nums, target, lower) 表示在 $\textit{nums}$ 数组中二分查找 $\textit{target}$ 的位置，如果 $\textit{lower}$ 为 $\rm true$，则查找第一个大于等于 $\textit{target}$ 的下标，否则查找第一个大于 $\textit{target}$ 的下标。

最后，因为 ​$\textit{target}$ 可能不存在数组中，因此我们需要重新校验我们得到的两个下标 ​$\textit{leftIdx}$ 和 ​$\textit{rightIdx}$，看是否符合条件，如果符合条件就返回 ​$[\textit{leftIdx},\textit{rightIdx}]$，不符合就返回 ​$[-1,-1]$。

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}
```

