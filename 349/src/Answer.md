# 349题解
考察点：哈希表，双指针

#### 方法一：两个集合

计算两个数组的交集，直观的方法是遍历数组 nums1，对于其中的每个元素，遍历数组 nums2 判断该元素是否在数组 nums2 中，如果存在，则将该元素添加到返回值。假设数组 nums1 和 nums2 的长度分别是 $m$ 和 $n$，则遍历数组 nums1 需要 $O(m)$ 的时间，判断 nums1 中的每个元素是否在数组 nums2 中需要 $O(n) $的时间，因此总时间复杂度是 $O(mn)$。

如果使用哈希集合存储元素，则可以在 $O(1)$ 的时间内判断一个元素是否在集合中，从而降低时间复杂度。

首先使用两个集合分别存储两个数组中的元素，然后遍历较小的集合，判断其中的每个元素是否在另一个集合中，如果元素也在另一个集合中，则将该元素添加到返回值。该方法的时间复杂度可以降低到 $O(m+n)$。

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> Nums1 = new HashSet<>();
        Set<Integer> Nums2 = new HashSet<>();
        for(int i : nums1){
            Nums1.add(i);
        }
        for(int j : nums2){
            if(Nums1.contains(j)){
                Nums2.add(j);
            }
        }
        int [] result = new int[Nums2.size()];
        int index = 0;
        for(int i:Nums2){
            result[index] = i;
            index++;
        }
        return result;
    }
}
```

#### 方法二：排序 + 双指针


智能模式

模拟面试

























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

