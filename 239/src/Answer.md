# 239题解
考察点：滑动窗口最大值

对于每个滑动窗口，我们可以使用 $O(k)$ 的时间遍历其中的每一个元素，找出其中的最大值。对于长度为 $n$ 的数组 $\textit{nums}$ 而言，窗口的数量为 $n−k+1$，因此该算法的时间复杂度为 $O((n-k+1)k)=O(nk)$，会超出时间限制，因此我们需要进行一些优化。

我们可以想到，对于两个相邻（只差了一个位置）的滑动窗口，它们共用着 $k−1$ 个元素，而只有 $1$ 个元素是变化的。我们可以根据这个特点进行优化。

#### 方法一：优先队列

思路与算法

对于「最大值」，我们可以想到一种非常合适的数据结构，那就是优先队列（堆），其中的大根堆可以帮助我们实时维护一系列元素中的最大值。

对于本题而言，初始时，我们将数组 $\textit{nums}$ 的前 $k$ 个元素放入优先队列中。每当我们向右移动窗口时，我们就可以把一个新的元素放入优先队列中，此时堆顶的元素就是堆中所有元素的最大值。然而这个最大值可能并不在滑动窗口中，在这种情况下，这个值在数组 $\textit{nums}$ 中的位置出现在滑动窗口左边界的左侧。因此，当我们后续继续向右移动窗口时，这个值就永远不可能出现在滑动窗口中了，我们可以将其永久地从优先队列中移除。

我们不断地移除堆顶的元素，直到其确实出现在滑动窗口中。此时，堆顶元素就是滑动窗口中的最大值。为了方便判断堆顶元素与滑动窗口的位置关系，我们可以在优先队列中存储二元组 $(\textit{num}, \textit{index})$，表示元素 $\textit{num}$ 在数组中的下标为 $\textit{index}$。

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] pair1, int[] pair2) {
                return pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1];
            }
        });
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{nums[i], i});
        }
        int[] ans = new int[n - k + 1];
        ans[0] = pq.peek()[0];
        for (int i = k; i < n; ++i) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
    }
}
```

#### 方法二：单调队列

思路与算法

我们可以顺着方法一的思路继续进行优化。

由于我们需要求出的是滑动窗口的最大值，如果当前的滑动窗口中有两个下标 $i$ 和 $j$，其中 $i$ 在 $j$ 的左侧（$i < j$），并且 $i$ 对应的元素不大于 $j$ 对应的元素（$\textit{nums}[i] \leq \textit{nums}[j]$），那么会发生什么呢？

当滑动窗口向右移动时，只要 $i$ 还在窗口中，那么 $j$ 一定也还在窗口中，这是 $i$ 在 $j$ 的左侧所保证的。因此，由于 $\textit{nums}[j]$ 的存在，$\textit{nums}[i]$ 一定不会是滑动窗口中的最大值了，我们可以将 $\textit{nums}[i]$ 永久地移除。

因此我们可以使用一个队列存储所有还没有被移除的下标。在队列中，这些下标按照从小到大的顺序被存储，并且它们在数组 $\textit{nums}$ 中对应的值是严格单调递减的。因为如果队列中有两个相邻的下标，它们对应的值相等或者递增，那么令前者为 $i$，后者为 $j$，就对应了上面所说的情况，即 $\textit{nums}[i]$ 会被移除，这就产生了矛盾。

当滑动窗口向右移动时，我们需要把一个新的元素放入队列中。为了保持队列的性质，我们会不断地将新的元素与队尾的元素相比较，如果前者大于等于后者，那么队尾的元素就可以被永久地移除，我们将其弹出队列。我们需要不断地进行此项操作，直到队列为空或者新的元素小于队尾的元素。

由于队列中下标对应的元素是严格单调递减的，因此此时队首下标对应的元素就是滑动窗口中的最大值。但与方法一中相同的是，此时的最大值可能在滑动窗口左边界的左侧，并且随着窗口向右移动，它永远不可能出现在滑动窗口中了。因此我们还需要不断从队首弹出元素，直到队首元素在窗口中为止。

为了可以同时弹出队首和队尾的元素，我们需要使用双端队列。满足这种单调性的双端队列一般称作「单调队列」。

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> deque = new LinkedList<Integer>();
        for (int i = 0; i < k; ++i) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        int[] ans = new int[n - k + 1];
        ans[0] = nums[deque.peekFirst()];
        for (int i = k; i < n; ++i) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;
    }
}
```

#### 方法三：分块 + 预处理

思路与算法

除了基于「随着窗口的移动实时维护最大值」的方法一以及方法二之外，我们还可以考虑其它有趣的做法。

我们可以将数组 $\textit{nums}$ 从左到右按照 $k$ 个一组进行分组，最后一组中元素的数量可能会不足 $k$ 个。如果我们希望求出 $\textit{nums}[i]$ 到 $\textit{nums}[i+k-1]$ 的最大值，就会有两种情况：

- 如果 $i$ 是 $k$ 的倍数，那么 $\textit{nums}[i]$ 到 $\textit{nums}[i+k-1]$ 恰好是一个分组。我们只要预处理出每个分组中的最大值，即可得到答案；

- 如果 $i$ 不是 $k$ 的倍数，那么 $\textit{nums}[i]$ 到 $\textit{nums}[i+k-1]$ 会跨越两个分组，占有第一个分组的后缀以及第二个分组的前缀。假设 $j$ 是 $k$ 的倍数，并且满足 $i < j \leq i+k-1$，那么 $\textit{nums}[i]$ 到 $\textit{nums}[j-1]$ 就是第一个分组的后缀，$\textit{nums}[j]$ 到 $\textit{nums}[i+k-1]$ 就是第二个分组的前缀。如果我们能够预处理出每个分组中的前缀最大值以及后缀最大值，同样可以在 $O(1)$ 的时间得到答案。


因此我们用 $\textit{prefixMax}[i]$ 表示下标 ii 对应的分组中，以 $i$ 结尾的前缀最大值；$\textit{suffixMax}[i]$ 表示下标 $i$ 对应的分组中，以 $i$ 开始的后缀最大值。它们分别满足如下的递推式

$\textit{prefixMax}[i]=\begin{cases} \textit{nums}[i], & \quad i ~是~ k ~的倍数 \\ \max\{ \textit{prefixMax}[i-1], \textit{nums}[i] \}, & \quad i ~不是~ k ~的倍数 \end{cases}$


以及

$\textit{suffixMax}[i]=\begin{cases} \textit{nums}[i], & \quad i+1 ~是~ k ~的倍数 \\ \max\{ \textit{suffixMax}[i+1], \textit{nums}[i] \}, & \quad i+1 ~不是~ k ~的倍数 \end{cases}$


需要注意在递推 $\textit{suffixMax}[i]$ 时需要考虑到边界条件 $\textit{suffixMax}[n-1]=\textit{nums}[n-1]$，而在递推 $\textit{prefixMax}[i]$ 时的边界条件 $\textit{prefixMax}[0]=\textit{nums}[0]$ 恰好包含在递推式的第一种情况中，因此无需特殊考虑。

在预处理完成之后，对于 $\textit{nums}[i]$ 到 $\textit{nums}[i+k-1]$ 的所有元素，如果 $i$ 不是 $k$ 的倍数，那么窗口中的最大值为 $\textit{suffixMax}[i]$ 与 $\textit{prefixMax}[i+k-1]$ 中的较大值；如果 $i$ 是 $k$ 的倍数，那么此时窗口恰好对应一整个分组，$\textit{suffixMax}[i]$ 和 $\textit{prefixMax}[i+k-1]$ 都等于分组中的最大值，因此无论窗口属于哪一种情况，

$\max\big\{ \textit{suffixMax}[i], \textit{prefixMax}[i+k-1] \big\}$

即为答案。
