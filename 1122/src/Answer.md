# 1122题解
考察点：数组的相对排序

#### 方法一：自定义排序

一种容易想到的方法是使用排序并自定义比较函数。

由于数组 $\textit{arr}_2$规定了比较顺序，因此我们可以使用哈希表对该顺序进行映射：即对于数组 $\textit{arr}_2$中的第 $i$ 个元素，我们将 $(\textit{arr}_2[i], i)$这一键值对放入哈希表 $\textit{rank}$ 中，就可以很方便地对数组 $\textit{arr}_1$中的元素进行比较。

比较函数的写法有很多种，例如我们可以使用最基础的比较方法，对于元素 $x$ 和 $y$：

- 如果 $x$ 和 $y$ 都出现在哈希表中，那么比较它们对应的值 $\textit{rank}[x]$ 和 $\textit{rank}[y]$；

- 如果 $x$ 和 $y$ 都没有出现在哈希表中，那么比较它们本身；

- 对于剩余的情况，出现在哈希表中的那个元素较小。


```java
class Solution {
    class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root, res);
        return res;
    }

    public void inorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }
}
}
```

#### 方法二：计数排序

思路与算法

注意到本题中元素的范围为 $[0, 1000]$，这个范围不是很大，我们也可以考虑不基于比较的排序，例如「计数排序」。

具体地，我们使用一个长度为 $1001$（下标从 $0$ 到 $1000$）的数组 $\textit{frequency}$，记录每一个元素在数组 $\textit{arr}_1$中出现的次数。随后我们遍历数组 $\textit{arr}_2$，当遍历到元素 $x$ 时，我们将 $\textit{frequency}[x]$ 个 $x$ 加入答案中，并将 $\textit{frequency}[x]$ 清零。当遍历结束后，所有在 $\textit{arr}_2$中出现过的元素就已经有序了。

此时还剩下没有在 $\textit{arr}_2$中出现过的元素，因此我们还需要对整个数组 $\textit{frequency}$ 进行一次遍历。当遍历到元素 $x$ 时，如果 $\textit{frequency}[x]$ 不为 $0$，我们就将 $\textit{frequency}[x]$ 个 $x$ 加入答案中。

细节

我们可以对空间复杂度进行一个小优化。实际上，我们不需要使用长度为 $1001$ 的数组，而是可以找出数组 $\textit{arr}_1$中的最大值 $\textit{upper}$，使用长度为 $\textit{upper}+1$ 的数组即可。

```java
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int upper = 0;
        for (int x : arr1) {
            upper = Math.max(upper, x);
        }
        int[] frequency = new int[upper + 1];
        for (int x : arr1) {
            ++frequency[x];
        }
        int[] ans = new int[arr1.length];
        int index = 0;
        for (int x : arr2) {
            for (int i = 0; i < frequency[x]; ++i) {
                ans[index++] = x;
            }
            frequency[x] = 0;
        }
        for (int x = 0; x <= upper; ++x) {
            for (int i = 0; i < frequency[x]; ++i) {
                ans[index++] = x;
            }
        }
        return ans;
    }
}
```

