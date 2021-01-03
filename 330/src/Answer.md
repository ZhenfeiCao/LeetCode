# 330题解
考察点：按要求补齐数组

#### 方法一：贪心算法

对于正整数 $x$，如果区间 $[1,x-1]$ 内的所有数字都已经被覆盖，且 $x$ 在数组中，则区间 $[1,2x-1]$ 内的所有数字也都被覆盖。证明如下。

对于任意 $1 \le y<x$，$y$ 已经被覆盖，$x$ 在数组中，因此 $y+x$ 也被覆盖，区间 $[x+1,2x-1]$（即区间 $[1,x-1]$ 内的每个数字加上 $x$ 之后得到的区间）内的所有数字也被覆盖，由此可得区间 $[1,2x-1]$ 内的所有数字都被覆盖。

假设数字 $x$ 缺失，则至少需要在数组中补充一个小于或等于 $x$ 的数，才能覆盖到 $x$，否则无法覆盖到 $x$。

如果区间 $[1,x-1]$ 内的所有数字都已经被覆盖，则从贪心的角度考虑，补充 $x$ 之后即可覆盖到 $x$，且满足补充的数字个数最少。在补充 $x$ 之后，区间 $[1,2x-1]$ 内的所有数字都被覆盖，下一个缺失的数字一定不会小于 $2x$。

由此可以提出一个贪心的方案。每次找到未被数组 $\textit{nums}$ 覆盖的最小的整数 $x$，在数组中补充 $x$，然后寻找下一个未被覆盖的最小的整数，重复上述步骤直到区间 $[1,n]$ 中的所有数字都被覆盖。

具体实现方面，任何时候都应满足区间 $[1,x-1]$ 内的所有数字都被覆盖。令 $x$ 的初始值为 $1$，数组下标 $\textit{index}$ 的初始值为 $0$，则初始状态下区间 $[1,x-1]$ 为空区间，满足区间内的所有数字都被覆盖。进行如下操作。

- 如果 $\textit{index}$ 在数组 $\textit{nums}$ 的下标范围内且 $\textit{nums}[\textit{index}] \le x$，则将 $\textit{nums}[\textit{index}]$ 的值加给 $x$，并将 $\textit{index}$ 的值加 $1$。
- - 被覆盖的区间从 $[1,x-1]$ 扩展到 $[1,x+\textit{nums}[\textit{index}]-1]$，对 $x$ 的值更新以后，被覆盖的区间为 $[1,x-1]$。
- 否则，$x$ 没有被覆盖，因此需要在数组中补充 $x$，然后将 $x$ 的值乘以 $2$。
- - 在数组中补充 $x$ 之后，被覆盖的区间从 $[1,x-1]$ 扩展到 $[1,2x-1]$，对 $x$ 的值更新以后，被覆盖的区间为 $[1,x-1]$。
- 重复上述操作，直到 $x$ 的值大于 $n$。

由于任何时候都满足区间 $[1,x-1]$ 内的所有数字都被覆盖，因此上述操作可以保证区间 $[1,n]$ 内的所有数字都被覆盖。

又由于上述操作只在 $x$ 不被覆盖时才在数组中补充 $x$，如果不补充 $x$ 则 $x$ 无法被覆盖，因此可以保证补充的数字个数最少。如果减少补充的数字个数，则无法覆盖区间 $[1,n]$ 内的所有数字。

```java
class Solution {
    public int minPatches(int[] nums, int n) {
        int patches = 0;
        long x = 1;
        int length = nums.length, index = 0;
        while (x <= n) {
            if (index < length && nums[index] <= x) {
                x += nums[index];
                index++;
            } else {
                x *= 2;
                patches++;
            }
        }
        return patches;
    }
}
```

























1



2

3

4



5

6

7



8



9

10

11

12

13

14



15







































































class Solution {

​    public int[] intersection(int[] nums1, int[] nums2) {

​        Set<Integer> Nums1 = new HashSet<>();

​        Set<Integer> Nums2 = new HashSet<>();

​        for(int i : nums1){

​            Nums1.add(i);

​        }

​        for(int j : nums2){

​            if(Nums1.contains(j)){

​                Nums2.add(j);

​            }

​        }

​        int [] result = new int[Nums2.size()];

​        int index = 0;

​        for(int i:Nums2){





















测试用例

代码执行结果

调试器Beta

已完成

执行用时：0 ms



输入

[1,2,2,1] [2,2] 

输出

[2]

差别

预期结果

[2]



控制台 如何创建一个测试用例?

执行代码提交





关闭上一题解1 / 752下一题解

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/users/leetcode-solution/avatar_1582018938.png?x-oss-process=image%2Fresize%2Ch_38%2Cw_38)

# 两个数组的交集

力扣官方题解

发布于 17 小时前

10.5k

官方

C

C++

Go

Java

JavaScript

Python

排序

哈希表

双指针

#### 方法一：两个集合

计算两个数组的交集，直观的方法是遍历数组 `nums1`，对于其中的每个元素，遍历数组 `nums2` 判断该元素是否在数组 `nums2` 中，如果存在，则将该元素添加到返回值。假设数组 `nums1` 和 `nums2` 的长度分别是 mm 和 nn，则遍历数组 `nums1` 需要 O(m)O(m) 的时间，判断 `nums1` 中的每个元素是否在数组 `nums2` 中需要 O(n)O(n) 的时间，因此总时间复杂度是 O(mn)O(mn)。

如果使用哈希集合存储元素，则可以在 O(1)O(1) 的时间内判断一个元素是否在集合中，从而降低时间复杂度。

首先使用两个集合分别存储两个数组中的元素，然后遍历较小的集合，判断其中的每个元素是否在另一个集合中，如果元素也在另一个集合中，则将该元素添加到返回值。该方法的时间复杂度可以降低到 O(m+n)O(m+n)。

- Java
- Python3
- C++
- JavaScript
- Golang
- C

```
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        for (int num : nums1) {
            set1.add(num);
        }
        for (int num : nums2) {
            set2.add(num);
        }
        return getIntersection(set1, set2);
    }

    public int[] getIntersection(Set<Integer> set1, Set<Integer> set2) {
        if (set1.size() > set2.size()) {
            return getIntersection(set2, set1);
        }
        Set<Integer> intersectionSet = new HashSet<Integer>();
        for (int num : set1) {
            if (set2.contains(num)) {
                intersectionSet.add(num);
            }
        }
        int[] intersection = new int[intersectionSet.size()];
        int index = 0;
        for (int num : intersectionSet) {
            intersection[index++] = num;
        }
        return intersection;
    }
}
```

**复杂度分析**

- 时间复杂度：O(m+n)O(m+n)，其中 mm 和 nn 分别是两个数组的长度。使用两个集合分别存储两个数组中的元素需要 O(m+n)O(m+n) 的时间，遍历较小的集合并判断元素是否在另一个集合中需要 O(\min(m,n))O(min(m,n)) 的时间，因此总时间复杂度是 O(m+n)O(m+n)。
- 空间复杂度：O(m+n)O(m+n)，其中 mm 和 nn 分别是两个数组的长度。空间复杂度主要取决于两个集合。

#### 方法二：排序 + 双指针

如果两个数组是有序的，则可以使用双指针的方法得到两个数组的交集。

首先对两个数组进行排序，然后使用两个指针遍历两个数组。可以预见的是加入答案的数组的元素一定是递增的，为了保证加入元素的唯一性，我们需要额外记录变量 $pre$ 表示上一次加入答案数组的元素。

初始时，两个指针分别指向两个数组的头部。每次比较两个指针指向的两个数组中的数字，如果两个数字不相等，则将指向较小数字的指针右移一位，如果两个数字相等，且该数字不等于 $pre$ ，将该数字添加到答案并更新 $pre$ 变量，同时将两个指针都右移一位。当至少有一个指针超出数组范围时，遍历结束。

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int length1 = nums1.length, length2 = nums2.length;
        int[] intersection = new int[length1 + length2];
        int index = 0, index1 = 0, index2 = 0;
        while (index1 < length1 && index2 < length2) {
            int num1 = nums1[index1], num2 = nums2[index2];
            if (num1 == num2) {
                // 保证加入元素的唯一性
                if (index == 0 || num1 != intersection[index - 1]) {
                    intersection[index++] = num1;
                }
                index1++;
                index2++;
            } else if (num1 < num2) {
                index1++;
            } else {
                index2++;
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }
}
```

