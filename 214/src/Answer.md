# 214题解
考察点：KMP算法

#### 方法一：KMP算法

KMP算法详解：<https://blog.csdn.net/yyzsir/article/details/89462339>

基本思路：s'+s构成回文串，则需要找到s中的回文子串a和后缀b，则最终只需翻转b+a+b构成回文串

next数组的含义为：到当前节点为止的字符串的最长公共前后缀的长度

算法中的关键细节：

为何需要next：用于在匹配当前字符串的最长公共前后缀的过程中可以根据先前的匹配结果跳过一些情况，减少遍历的次数

为何新建Len+1长度数组：因为当前位置指到该节点前（不包括该节点）的子串中的最长公共前后缀的长度，为了得到原字符串的长度，需要长度+1

pointer = next[pointer]：直接跳转到之前已经匹配过的最长公共前后缀中的后缀部分，不重新将指针归0，方便寻找下一次的情况

```java
class Solution {
    public String shortestPalindrome(String s) {
        if(s.equals("")){
            return "";
        }
        String reverseStr = new StringBuilder(s).reverse().toString();
        String pattern = s + '#' + reverseStr;
        int maxLen = computeNext(pattern);
        return reverseStr.substring(0, reverseStr.length() - maxLen) + s;
    }

    public int computeNext(String pattern){
        int [] next = new int[pattern.length() + 1];
        next[0] = -1;
        next[1] = 0; //长度为1的字符串没有前后缀
        int i = 2; //next数组索引
        int pointer = 0; //指针指向pattern的位置
        while(i<next.length){
            if(pattern.charAt(i-1) == pattern.charAt(pointer)){
                next[i] = pointer + 1;
                pointer = next[i];
                i++;
            }else if(pointer == 0){
                next[i] = 0;
                i++;
            }else{
                pointer = next[pointer];
            }
        }
        return next[next.length-1];
    }
}
```
