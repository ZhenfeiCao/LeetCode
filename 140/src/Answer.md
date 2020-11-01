# 140题解
考察点：单词拆分 II

前言
这道题是「139. 单词拆分」的进阶，第 139 题要求判断是否可以拆分，这道题要求返回所有可能的拆分结果。

第 139 题可以使用动态规划的方法判断是否可以拆分，因此这道题也可以使用动态规划的思想。但是这道题如果使用自底向上的动态规划的方法进行拆分，则无法事先判断拆分的可行性，在不能拆分的情况下会超时。

例如以下例子，由于字符串 $s$ 中包含字母 $\texttt{b}$，而单词列表 $\textit{wordDict}$ 中的所有单词都由字母 $\texttt{a}$ 组成，不包含字母 $\texttt{b}$，因此不能拆分，但是自底向上的动态规划仍然会在每个下标都进行大量的匹配，导致超时。

```
s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
wordDict = ["a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"]
```

为了避免动态规划的方法超时，需要首先使用第 139 题的代码进行判断，在可以拆分的情况下再使用动态规划的方法进行拆分。相比之下，自顶向下的记忆化搜索可以在搜索过程中将不可以拆分的情况进行剪枝，因此记忆化搜索是更优的做法。

#### 方法一：动态规划求是否有解、回溯算法求所有具体解

思路：

- 动态规划得到了原始输入字符串的任意长度的 前缀子串 是否可以拆分为单词集合中的单词；

- 我们以示例 2：s = "pineapplepenapple"、wordDict = ["apple", "pen", "applepen", "pine", "pineapple"] 为例，分析如何得到所有具体解。

所有任意长度的前缀是否可拆分是知道的，那么如果 **后缀子串在单词集合中**，这个后缀子串就是解的一部分，例如：

![](https://pic.leetcode-cn.com/1604156019-RPUqqc-image.png)

根据这个思路，可以画出树形结构如下。

![](https://pic.leetcode-cn.com/1604155779-zGMFLS-image.png)

再对比这个问题的输出：

```
[
  "pine apple pen apple",
  "pineapple pen apple",
  "pine applepen apple"
]
```

可以发现，树形结构中，从叶子结点到根结点的路径是符合要求的一个解，与以前做过的回溯算法的问题不一样，这个时候路径变量我们需要在依次在列表的开始位置插入元素，可以使用队列（LinkedList）实现，或者是双端队列（ArrayDeque）。

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public List<String> wordBreak(String s, List<String> wordDict) {
        // 为了快速判断一个单词是否在单词集合中，需要将它们加入哈希表
        Set<String> wordSet = new HashSet<>(wordDict);
        int len = s.length();

        // 第 1 步：动态规划计算是否有解
        // dp[i] 表示「长度」为 i 的 s 前缀子串可以拆分成 wordDict 中的单词
        // 长度包括 0 ，因此状态数组的长度为 len + 1
        boolean[] dp = new boolean[len + 1];
        // 0 这个值需要被后面的状态值参考，如果一个单词正好在 wordDict 中，dp[0] 设置成 true 是合理的
        dp[0] = true;
        int maxLen = 0;
        for(int i=0;i<wordDict.size();i++){
            maxLen = Math.max(maxLen,wordDict.get(i).length());
        }
        for(int i=1;i<dp.length;i++){
            for(int j=i;j>=i-maxLen&&j>=0;j--){
                if(dp[j]&&wordDict.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }

        // 第 2 步：回溯算法搜索所有符合条件的解
        List<String> res = new ArrayList<>();
        if (dp[len]) {
            Deque<String> path = new ArrayDeque<>();
            dfs(s, len, wordSet, dp, path, res);
            return res;
        }
        return res;
    }

    /**
     * s[0:len) 如果可以拆分成 wordSet 中的单词，把递归求解的结果加入 res 中
     *
     * @param s
     * @param len     长度为 len 的 s 的前缀子串
     * @param wordSet 单词集合，已经加入哈希表
     * @param dp      预处理得到的 dp 数组
     * @param path    从叶子结点到根结点的路径
     * @param res     保存所有结果的变量
     */
    private void dfs(String s, int len, Set<String> wordSet, boolean[] dp, Deque<String> path, List<String> res) {
        if (len == 0) {
            res.add(String.join(" ",path));
            return;
        }

        // 可以拆分的左边界从 len - 1 依次枚举到 0
        for (int i = len - 1; i >= 0; i--) {
            String suffix = s.substring(i, len);
            if (wordSet.contains(suffix) && dp[i]) {
                path.addFirst(suffix);
                dfs(s, i, wordSet, dp, path, res);
                path.removeFirst();
            }
        }
    }
}
```

#### 方法二：记忆化搜索

对于字符串 $s$，如果某个前缀是单词列表中的单词，则拆分出该单词，然后对 $s$ 的剩余部分继续拆分。如果可以将整个字符串 $s$ 拆分成单词列表中的单词，则得到一个句子。在对 $s$ 的剩余部分拆分得到一个句子之后，将拆分出的第一个单词（即 $s$ 的前缀）添加到句子的头部，即可得到一个完整的句子。上述过程可以通过回溯实现。

假设字符串 $s$ 的长度为 $n$，回溯的时间复杂度在最坏情况下高达 $O(n^n)$。时间复杂度高的原因是存在大量重复计算，可以通过记忆化的方式降低时间复杂度。

具体做法是，使用哈希表存储字符串 $s$ 的每个下标和从该下标开始的部分可以组成的句子列表，在回溯过程中如果遇到已经访问过的下标，则可以直接从哈希表得到结果，而不需要重复计算。如果到某个下标发现无法匹配，则哈希表中该下标对应的是空列表，因此可以对不能拆分的情况进行剪枝优化。

还有一个可优化之处为使用哈希集合存储单词列表中的单词，这样在判断一个字符串是否是单词列表中的单词时只需要判断该字符串是否在哈希集合中即可，而不再需要遍历单词列表。

```java
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<Integer, List<List<String>>> map = new HashMap<Integer, List<List<String>>>();
        List<List<String>> wordBreaks = backtrack(s, s.length(), new HashSet<String>(wordDict), 0, map);
        List<String> breakList = new LinkedList<String>();
        for (List<String> wordBreak : wordBreaks) {
            breakList.add(String.join(" ", wordBreak));
        }
        return breakList;
    }

    public List<List<String>> backtrack(String s, int length, Set<String> wordSet, int index, Map<Integer, List<List<String>>> map) {
        if (!map.containsKey(index)) {
            List<List<String>> wordBreaks = new LinkedList<List<String>>();
            if (index == length) {
                wordBreaks.add(new LinkedList<String>());
            }
            for (int i = index + 1; i <= length; i++) {
                String word = s.substring(index, i);
                if (wordSet.contains(word)) {
                    List<List<String>> nextWordBreaks = backtrack(s, length, wordSet, i, map);
                    for (List<String> nextWordBreak : nextWordBreaks) {
                        LinkedList<String> wordBreak = new LinkedList<String>(nextWordBreak);
                        wordBreak.offerFirst(word);
                        wordBreaks.add(wordBreak);
                    }
                }
            }
            map.put(index, wordBreaks);
        }
        return map.get(index);
    }
}
```
