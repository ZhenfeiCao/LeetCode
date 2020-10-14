# 1002题解
考察点：查找常用字符

#### 方法一：计数

思路与算法

根据题目的要求，如果字符 $c$ 在所有字符串中均出现了 $k$ 次及以上，那么最终答案中需要包含 $k$ 个 $c$。因此，我们可以使用 $minfreq[c]$ 存储字符 $c$ 在所有字符串中出现次数的最小值。

我们可以依次遍历每一个字符串。当我们遍历到字符串 $s$ 时，我们使用 $freq[c]$ 统计 $s$ 中每一个字符 $c$ 出现的次数。在统计完成之后，我们再将每一个 $minfreq[c]$ 更新为其本身与 $freq[c]$ 的较小值。这样一来，当我们遍历完所有字符串后，$minfreq[c]$ 就存储了字符 $c$ 在所有字符串中出现次数的最小值。

由于题目保证了所有的字符均为小写字母，因此我们可以用长度为 2626 的数组分别表示 $minfreq$ 以及 $freq$。

在构造最终的答案时，我们遍历所有的小写字母 $c$，并将 $minfreq[c]$ 个 $c$ 添加进答案数组即可。

```java
class Solution {
    public List<String> commonChars(String[] A) {
        int[] minfreq = new int[26];
        Arrays.fill(minfreq, Integer.MAX_VALUE);
        for (String word: A) {
            int[] freq = new int[26];
            int length = word.length();
            for (int i = 0; i < length; ++i) {
                char ch = word.charAt(i);
                ++freq[ch - 'a'];
            }
            for (int i = 0; i < 26; ++i) {
                minfreq[i] = Math.min(minfreq[i], freq[i]);
            }
        }

        List<String> ans = new ArrayList<String>();
        for (int i = 0; i < 26; ++i) {
            for (int j = 0; j < minfreq[i]; ++j) {
                ans.add(String.valueOf((char) (i + 'a')));
            }
        }
        return ans;
    }
}
```
