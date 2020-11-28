# 454题解
考察点：四数相加 II

解题思路
多数运算结果等于指定值问题都可转为两数相加
超过两数就通过枚举固定多出数。本题转为A B + C D = 0
即：C + D = 0 - A - B，枚举A B组合，以0 - A - B作键名存A B组合数
枚举C D组合，C + D在哈希表中，就找到A B组合数种可能。累加即可

#### 方法一：分组 + 哈希表

思路与算法

我们可以将四个数组分成两部分，$A$ 和 $B$ 为一组，$C$ 和 $D$ 为另外一组。

对于 $A$ 和 $B$，我们使用二重循环对它们进行遍历，得到所有 $A[i]+B[j]$ 的值并存入哈希映射中。对于哈希映射中的每个键值对，每个键表示一种 $A[i]+B[j]$，对应的值为 $A[i]+B[j]$ 出现的次数。

对于 $C$ 和 $D$，我们同样使用二重循环对它们进行遍历。当遍历到 $C[k]+D[l]$ 时，如果 $−(C[k]+D[l])$ 出现在哈希映射中，那么将 $−(C[k]+D[l])$ 对应的值累加进答案中。

最终即可得到满足 $A[i]+B[j]+C[k]+D[l]=0$ 的四元组数目。

```java
class Solution {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> countAB = new HashMap<Integer, Integer>();
        for (int u : A) {
            for (int v : B) {
                countAB.put(u + v, countAB.getOrDefault(u + v, 0) + 1);
            }
        }
        int ans = 0;
        for (int u : C) {
            for (int v : D) {
                if (countAB.containsKey(-u - v)) {
                    ans += countAB.get(-u - v);
                }
            }
        }
        return ans;
    }
}
```

