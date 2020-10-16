# 977题解
考察点：有序数组的平方

#### 方法一：直接排序

**思路与算法**

最简单的方法就是将数组 AA 中的数平方后直接排序。

**代码**

```java
class Solution {
    public int[] sortedSquares(int[] A) {
        int[] ans = new int[A.length];
        for (int i = 0; i < A.length; ++i) {
            ans[i] = A[i] * A[i];
        }
        Arrays.sort(ans);
        return ans;
    }
}
```

#### 方法二：双指针

思路与算法

方法一没有利用「数组 $A$ 已经按照升序排序」这个条件。显然，如果数组 $A$ 中的所有数都是非负数，那么将每个数平方后，数组仍然保持升序；如果数组 $A$ 中的所有数都是负数，那么将每个数平方后，数组会保持降序。

这样一来，如果我们能够找到数组 $A$ 中负数与非负数的分界线，那么就可以用类似「归并排序」的方法了。具体地，我们设 $neg$ 为数组 $A$ 中负数与非负数的分界线，也就是说，$A[0]$ 到 $A[neg]$ 均为负数，而 $A[neg+1]$ 到 $A[n−1]$ 均为非负数。当我们将数组 $A$ 中的数平方后，那么 $A[0]$ 到 $A[neg]$ 单调递减，$A[neg+1]$ 到 $A[n−1]$ 单调递增。

由于我们得到了两个已经有序的子数组，因此就可以使用归并的方法进行排序了。具体地，使用两个指针分别指向位置 $neg$ 和 $neg+1$，每次比较两个指针对应的数，选择较小的那个放入答案并移动指针。当某一指针移至边界时，将另一指针还未遍历到的数依次放入答案。

```java
class Solution {
    public int[] sortedSquares(int[] A) {
        int[] squares = new int[A.length];
        int neg = -1;
        for(int i=0;i<A.length;i++){
            if(A[i]<0){
                neg++;
            }else{
                break;
            }
        }
        int negPlus1 = neg+1;
        int pos = 0;
        while(neg>=0 || negPlus1<A.length){
            if(neg>=0 && negPlus1 < A.length){
                if(A[neg]*A[neg]>A[negPlus1]*A[negPlus1]){
                    squares[pos] = A[negPlus1]*A[negPlus1];
                    negPlus1++;
                }else{
                    squares[pos] = A[neg]*A[neg];
                    neg--;
                }
            }else if(neg<0){
                squares[pos] = A[negPlus1]*A[negPlus1];
                negPlus1++;
            }else{
                squares[pos] = A[neg]*A[neg];
                neg--;
            }
            pos++;
        }
        return squares;
    }
}

```

#### 方法三

同样地，我们可以使用两个指针分别指向位置 $0$ 和 $n−1$，每次比较两个指针对应的数，选择较大的那个逆序放入答案并移动指针。这种方法无需处理某一指针移动至边界的情况，读者可以仔细思考其精髓所在。

```java
class Solution {
    public int[] sortedSquares(int[] A) {
        int[] squares = new int[A.length];
        int head = 0;
        int tail = A.length - 1;
        for(int i=A.length-1;i>=0;i--){
            if(A[head]*A[head]>A[tail]*A[tail]){
                squares[i] = A[head]*A[head];
                head++;
            }else{
                squares[i] = A[tail]*A[tail];
                tail--;
            }
        }
        return squares;
    }
}
```

