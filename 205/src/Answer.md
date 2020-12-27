# 205题解
考察点：同构字符串

#### 方法一：哈希表

此题是「290. 单词规律」的简化版，需要我们判断 $s$ 和 $t$ 每个位置上的字符是否都一一对应，即 $s$ 的任意一个字符被 $t$ 中唯一的字符对应，同时 $t$ 的任意一个字符被 $s$ 中唯一的字符对应。这也被称为「双射」的关系。

以示例 $2$ 为例，$t$ 中的字符 $a$ 和 $r$ 虽然有唯一的映射 $o$，但对于 $s$ 中的字符 $o$ 来说其存在两个映射 ${a,r}$，故不满足条件。

因此，我们维护两张哈希表，第一张哈希表 $\textit{s2t}$ 以 $s$ 中字符为键，映射至 $t$ 的字符为值，第二张哈希表 $\textit{t2s}$ 以 $t $ 中字符为键，映射至 $s$ 的字符为值。从左至右遍历两个字符串的字符，不断更新两张哈希表，如果出现冲突（即当前下标 $\textit{index}$ 对应的字符 $s[\textit{index}]$ 已经存在映射且不为 $t[\textit{index}]$ 或当前下标 $\textit{index}$ 对应的字符 $t[\textit{index}]$ 已经存在映射且不为 $s[\textit{index}]$）时说明两个字符串无法构成同构，返回 $\rm false$。

如果遍历结束没有出现冲突，则表明两个字符串是同构的，返回 $\rm true$ 即可。

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> s2t = new HashMap<Character, Character>();
        Map<Character, Character> t2s = new HashMap<Character, Character>();
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            char x = s.charAt(i), y = t.charAt(i);
            if ((s2t.containsKey(x) && s2t.get(x) != y) || (t2s.containsKey(y) && t2s.get(y) != x)) {
                return false;
            }
            s2t.put(x, y);
            t2s.put(y, x);
        }
        return true;
    }
}
```

