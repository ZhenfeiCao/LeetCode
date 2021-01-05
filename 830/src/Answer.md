# 830题解
考察点：较大分组的位置

#### 方法一：一次遍历

思路及解法

我们可以遍历该序列，并记录当前分组的长度。如果下一个字符与当前字符不同，或者已经枚举到字符串尾部，就说明当前字符为当前分组的尾部。每次找到当前分组的尾部时，如果该分组长度达到 3，我们就将其加入答案。


```java
class Solution {
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        int n = s.length();
        int num = 1;
        for (int i = 0; i < n; i++) {
            if (i == n - 1 || s.charAt(i) != s.charAt(i + 1)) {
                if (num >= 3) {
                    ret.add(Arrays.asList(i - num + 1, i));
                }
                num = 1;
            } else {
                num++;
            }
        }
        return ret;
    }
}
```
