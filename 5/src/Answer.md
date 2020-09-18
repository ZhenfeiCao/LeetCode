# 5.题解
考察点：最长公共回文字符串

#### 方法一：动态规划

对于一个子串而言，如果它是回文串，并且长度大于 22，那么将它首尾的两个字母去除之后，它仍然是个回文串。例如对于字符串 “ababa”，如果我们已经知道 “bab” 是回文串，那么 “ababa” 一定是回文串，这是因为它的首尾两个字母都是 “a”。

根据这样的思路，我们就可以用动态规划的方法解决本题。我们用 P(i,j) 表示字符串 s 的第 i 到 j 个字母组成的串（下文表示成 s[i:j]）是否为回文串：

$$ P(i,j)=\left\{
\begin{array}{rcl}
true      &      & 如果子串S_i...S_j是回文串\\
false     &      & 其他\\
\end{array} \right. $$​	


这里的「其它情况」包含两种可能性：

- s[i, j] 本身不是一个回文串；


- i > j，此时 s[i, j] 本身不合法。


那么我们就可以写出动态规划的状态转移方程：

$P(i,j)=P(i+1,j-1) \&\& （S_i,S_j）$

也就是说，只有 s[i+1:j−1] 是回文串，并且 s 的第 i 和 j 个字母相同时，s[i:j] 才会是回文串。

上文的所有讨论是建立在子串长度大于 2 的前提之上的，我们还需要考虑动态规划中的边界条件，即子串的长度为 1 或 2。对于长度为 1 的子串，它显然是个回文串；对于长度为 2 的子串，只要它的两个字母相同，它就是一个回文串。因此我们就可以写出动态规划的边界条件：

$$ \left\{
\begin{array}{rcl}
P(i,i)&=&true\\
P(i,i+1)  &=&(S_i==S_{i+1})) \\
\end{array} \right. $$	


根据这个思路，我们就可以完成动态规划了，最终的答案即为所有 P(i,j)=true 中 j-i+1（即子串长度）的最大值。注意：在状态转移方程中，我们是从长度较短的字符串向长度较长的字符串进行转移的，因此一定要注意动态规划的循环顺序。

```java
class Solution {
    public String longestPalindrome(String s) {
        String result = "";
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int len=0;len<s.length();len++){
            for(int i=0;i+len<s.length();i++){
                if(i==i+len){
                    dp[i][i] = true;
                }else if(i+1==i+len){
                    dp[i][i+len] = s.charAt(i)==s.charAt(i+len);
                }else{
                    dp[i][i+len] = dp[i+1][i+len-1] && s.charAt(i)==s.charAt(i+len);
                }
                if(dp[i][i+len] && len+1>result.length()){
                    result = s.substring(i,i+len+1);
                }
            }
        }
        return result;
    }
}
```

#### 方法二：中心扩展算法

我们仔细观察一下方法一中的状态转移方程：

$$ \left\{
\begin{array}{rcl}
P(i,i) & = &true\\
P(i,i+1) &= &(S_i==S_{i+1})) \\
P(i,j) &= & P(i+1,j-1) \&\& (S_i==S_{i+1}) \\
\end{array} \right. $$	

找出其中的状态转移链：
P(i,j)←P(i+1,j−1)←P(i+2,j−2)←⋯←某一边界情况

可以发现，所有的状态在转移的时候的可能性都是唯一的。也就是说，我们可以从每一种边界情况开始「扩展」，也可以得出所有的状态对应的答案。

边界情况即为子串长度为 1 或 2 的情况。我们枚举每一种边界情况，并从对应的子串开始不断地向两边扩展。如果两边的字母相同，我们就可以继续扩展，例如从 P(i+1,j−1) 扩展到 P(i,j)；如果两边的字母不同，我们就可以停止扩展，因为在这之后的子串都不能是回文串了。

聪明的读者此时应该可以发现，「边界情况」对应的子串实际上就是我们「扩展」出的回文串的「回文中心」。方法二的本质即为：我们枚举所有的「回文中心」并尝试「扩展」，直到无法扩展为止，此时的回文串长度即为此「回文中心」下的最长回文串长度。我们对所有的长度求出最大值，即可得到最终的答案。

```java
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return right - left - 1;
    }
}
```

