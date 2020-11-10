# 973题解
考察点：最接近原点的 K 个点

前言
当我们计算出每个点到原点的欧几里得距离的平方后，本题和「剑指 Offer 40. 最小的k个数」是完全一样的题。

为什么是欧几里得距离的「平方」？这是因为欧几里得距离并不一定是个整数，在进行计算和比较时可能会引进误差；但它的平方一定是个整数，这样我们就无需考虑误差了。

#### 方法一：排序

**思路和算法**

将每个点到原点的欧几里得距离的平方从小到大排序后，取出前 $K$ 个即可。

```java
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] point1, int[] point2) {
                return (point1[0] * point1[0] + point1[1] * point1[1]) - (point2[0] * point2[0] + point2[1] * point2[1]);
            }
        });
        return Arrays.copyOfRange(points, 0, K);
    }
}
```

#### 方法二：优先队列

思路和算法

我们可以使用一个优先队列（大根堆）实时维护前 $K$ 个最小的距离平方。

首先我们将前 $K$ 个点的编号（为了方便最后直接得到答案）以及对应的距离平方放入优先队列中，随后从第 $K+1$ 个点开始遍历：如果当前点的距离平方比堆顶的点的距离平方要小，就把堆顶的点弹出，再插入当前的点。当遍历完成后，所有在优先队列中的点就是前 $K$ 个距离最小的点。

不同的语言提供的优先队列的默认情况不一定相同。在 C++ 语言中，优先队列即为大根堆，但在 Python 语言中，优先队列为小根堆，因此我们需要在优先队列中存储（以及比较）距离平方的相反数。

```java
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] array1, int[] array2) {
                return array2[0] - array1[0];
            }
        });
        for (int i = 0; i < K; ++i) {
            pq.offer(new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i});
        }
        int n = points.length;
        for (int i = K; i < n; ++i) {
            int dist = points[i][0] * points[i][0] + points[i][1] * points[i][1];
            if (dist < pq.peek()[0]) {
                pq.poll();
                pq.offer(new int[]{dist, i});
            }
        }
        int[][] ans = new int[K][2];
        for (int i = 0; i < K; ++i) {
            ans[i] = points[pq.poll()[1]];
        }
        return ans;
    }
}
```

#### 方法三：快速选择（快速排序的思想）

思路和算法

我们也可以借鉴快速排序的思想。

快速排序中的划分操作每次执行完后，都能将数组分成两个部分，其中小于等于分界值 $\textit{pivot}$ 的元素都会被放到左侧部分，而大于 $\textit{pivot}$ 的元素都都会被放到右侧部分。与快速排序不同的是，在本题中我们可以根据 $K$ 与 $\textit{pivot}$ 下标的位置关系，只处理划分结果的某一部分（而不是像快速排序一样需要处理两个部分）。

我们定义函数 random_select(left, right, K) 表示划分数组 $\textit{points}$ 的 $[\textit{left},\textit{right}]$ 区间，并且需要找到其中第 $K$ 个距离最小的点。在一次划分操作完成后，设 $\textit{pivot}$ 的下标为 $i$，即区间 $[\textit{left}, i-1]$ 中的点的距离都小于等于 $\textit{pivot}$，而区间 $[i+1,\textit{right}]$ 的点的距离都大于 $\textit{pivot}$。此时会有三种情况：

- 如果 $K = i-\textit{left}+1$，那么说明 $\textit{pivot}$ 就是第 $K$ 个距离最小的点，我们可以结束整个过程；

- 如果 $K < i-\textit{left}+1$，那么说明第 $K$ 个距离最小的点在 $\textit{pivot}$ 左侧，因此递归调用 random_select(left, i - 1, K)；

- 如果 $K > i-\textit{left}+1$，那么说明第 $K$ 个距离最小的点在 $\textit{pivot}$ 右侧，因此递归调用 random_select(i + 1, right, K - (i - left + 1))。


在整个过程结束之后，第 $K$ 个距离最小的点恰好就在数组 $\textit{points}$ 中的第 $K$ 个位置，并且其左侧的所有点的距离都小于它。此时，我们就找到了前 $K$ 个距离最小的点。

```java
class Solution {
    Random rand = new Random();

    public int[][] kClosest(int[][] points, int K) {
        int n = points.length;
        random_select(points, 0, n - 1, K);
        return Arrays.copyOfRange(points, 0, K);
    }

    public void random_select(int[][] points, int left, int right, int K) {
        int pivotId = left + rand.nextInt(right - left + 1);
        int pivot = points[pivotId][0] * points[pivotId][0] + points[pivotId][1] * points[pivotId][1];
        swap(points, right, pivotId);
        int i = left - 1;
        for (int j = left; j < right; ++j) {
            int dist = points[j][0] * points[j][0] + points[j][1] * points[j][1];
            if (dist <= pivot) {
                ++i;
                swap(points, i, j);
            }
        }
        ++i;
        swap(points, i, right);
        // [left, i-1] 都小于等于 pivot, [i+1, right] 都大于 pivot
        if (K < i - left + 1) {
            random_select(points, left, i - 1, K);
        } else if (K > i - left + 1) {
            random_select(points, i + 1, right, K - (i - left + 1));
        }
    }

    public void swap(int[][] points, int index1, int index2) {
        int[] temp = points[index1];
        points[index1] = points[index2];
        points[index2] = temp;
    }
}
```

