# 1356题解
考察点：根据数字二进制下 1 的数目排序

题目本身很简单，只要调用系统自带的排序函数，然后自己改写一下排序规则即可，所以这里主要讲讲如何计算数字二进制下 $1$ 的个数 。

#### 方法一：暴力

对每个十进制的数转二进制的时候统计一下 1 的个数即可。

```java
class Solution {
    public int[] sortByBits(int[] arr) {
        int[] bit = new int[10001];
        List<Integer> list = new ArrayList<Integer>();
        for (int x : arr) {
            list.add(x);
            bit[x] = get(x);
        }
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer x, Integer y) {
                if (bit[x] != bit[y]) {
                    return bit[x] - bit[y];
                } else {
                    return x - y;
                }
            }
        });
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public int get(int x) {
        int res = 0;
        while (x != 0) {
            res += x % 2;
            x /= 2;
        }
        return res;
    }
}
```

#### 方法二：递推预处理

我们定义 $bit[i]$ 为数字 $i$ 二进制表示下数字 1 的个数，则可以列出递推式：

$bit[i] = bit[i>>1] + (i \& 1)$

所以我们线性预处理 $bit$ 数组然后去排序即可。

```java
class Solution {
    public int[] sortByBits(int[] arr) {
        List<Integer> list = new ArrayList<Integer>();
        for (int x : arr) {
            list.add(x);
        }
        int[] bit = new int[10001];
        for (int i = 1; i <= 10000; ++i) {
            bit[i] = bit[i >> 1] + (i & 1);
        }
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer x, Integer y) {
                if (bit[x] != bit[y]) {
                    return bit[x] - bit[y];
                } else {
                    return x - y;
                }
            }
        });
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
```

#### 方法三：巧算

循环并使用 Integer.bitCount 计算数字中1的个数，乘以10000000（题目中不会大于 10^4）然后加上原数字，放入数组 map 中，并对 map 进行排序，最后 % 10000000 获取原来的数组，填充到原数组返回即可。

```java
class Solution {
    public int[] sortByBits(int[] arr) {
        int[] map = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            map[i] = Integer.bitCount(arr[i]) * 10000000 + arr[i];
        }
        Arrays.sort(map);
        for (int i = 0; i < map.length; i++) {
            map[i] = map[i] % 10000000;
        }
        return map;
    }
}
```

