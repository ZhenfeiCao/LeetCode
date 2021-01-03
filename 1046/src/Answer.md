# 1046题解
考察点：最后一块石头的重量

#### 方法一：最大堆

**思路及解法**

将所有石头的重量放入最大堆中。每次依次从队列中取出最重的两块石头 $a$ 和 $b$，必有 $a \ge b $。如果 $a>b$，则将新石头 $a−b$ 放回到最大堆中；如果 $a=b$，两块石头完全被粉碎，因此不会产生新的石头。重复上述操作，直到剩下的石头少于 $2$ 块。

最终可能剩下 $1$ 块石头，该石头的重量即为最大堆中剩下的元素，返回该元素；也可能没有石头剩下，此时最大堆为空，返回 $0$。

```java
class Solution {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        for (int stone : stones) {
            pq.offer(stone);
        }

        while (pq.size() > 1) {
            int a = pq.poll();
            int b = pq.poll();
            if (a > b) {
                pq.offer(a - b);
            }
        }
        return pq.isEmpty() ? 0 : pq.poll();
    }
}
```

#### 方法二：计数排序

思路与算法

第一眼看到题目想到得是排序，但是在排序后如果拿出得两个值不同又需要重新排序，复杂度感觉有点高，就没有进行尝试，然后看了一下数据发现0 < stones[i] <= 1000。
发现可以定义一个vector<int>mp(1005),用来存贮每个数字出现得次数，然后从1000从后往前遍历，定义一个标志位flag 和 一个答案value,当flag == 0 && mp[i]%2==0时，说明这样得石头是偶数，最后都会消失，继续往前遍历，当mp[i]%2==1时，两两消失，最后还会剩下一个，这时flag置为1，value = i。往前遍历，当遇到mp[i] != 0时这时flag = 1, value 为之前剩下得一个石头，mp[i] != 0, 这时用value -= i, 判断value 是否小于等于i, 用while循环进行相减，当value <= i || mp[i] == 0时跳出
判断value是否大于i，从而判断是那种情况跳出，value大于i说明mp[i] == 0了，这时continue,继续向前找，或者value小于等于i跳出，这时mp[value]++,再来判断mp[i]%2
是否等0，等于0时，将flag，value重新置为0，否则flag = 1，value = i，然后向前遍历，最后跳出循环，value即为所求答案