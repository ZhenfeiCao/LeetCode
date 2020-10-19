# 844题解
考察点：比较含退格的字符串

#### 方法一：重构字符串

思路及算法

最容易想到的方法是将给定的字符串中的退格符和应当被删除的字符都去除，还原给定字符串的一般形式。然后直接比较两字符串是否相等即可。

具体地，我们用栈处理遍历过程，每次我们遍历到一个字符：

- 如果它是退格符，那么我们将栈顶弹出；

- 如果它是普通字符，那么我们将其压入栈中。


```java
class Solution {
    public boolean backspaceCompare(String S, String T) {
        StringBuffer SBuffer = new StringBuffer("");
        StringBuffer TBuffer = new StringBuffer("");
        for(int i=0;i<S.length();i++){
            if(S.charAt(i)=='#'){
                if(!SBuffer.toString().equals("")){
                    SBuffer.deleteCharAt(SBuffer.length()-1);
                }
            }else{
                SBuffer.append(S.charAt(i));
            }
        }
        for(int i=0;i<T.length();i++){
            if(T.charAt(i)=='#'){
                if(!TBuffer.toString().equals("")){
                    TBuffer.deleteCharAt(TBuffer.length()-1);
                }
            }else{
                TBuffer.append(T.charAt(i));
            }
        }
        return SBuffer.toString().equals(TBuffer.toString());
    }
}
```

#### 方法二：双指针

思路及算法

一个字符是否会被删掉，只取决于该字符后面的退格符，而与该字符前面的退格符无关。因此当我们逆序地遍历字符串，就可以立即确定当前字符是否会被删掉。

具体地，我们定义 $skip$ 表示当前待删除的字符的数量。每次我们遍历到一个字符：

- 若该字符为退格符，则我们需要多删除一个普通字符，我们让$skip$ 加 $1$；

- 若该字符为普通字符：

- - 若 $skip$ 为 $0$，则说明当前字符不需要删去；

- - 若 $skip$ 不为 $0$，则说明当前字符需要删去，我们让$skip$ 减 $1$。


这样，我们定义两个指针，分别指向两字符串的末尾。每次我们让两指针逆序地遍历两字符串，直到两字符串能够各自确定一个字符，然后将这两个字符进行比较。重复这一过程直到找到的两个字符不相等，或遍历完字符串为止。

```java
class Solution {
    public boolean backspaceCompare(String S, String T) {
        int i = S.length() - 1, j = T.length() - 1;
        int skipS = 0, skipT = 0;

        while (i >= 0 || j >= 0) {
            while (i >= 0) {
                if (S.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break;
                }
            }
            while (j >= 0) {
                if (T.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }
            if (i >= 0 && j >= 0) {
                if (S.charAt(i) != T.charAt(j)) {
                    return false;
                }
            } else {
                if (i >= 0 || j >= 0) {
                    return false;
                }
            }
            i--;
            j--;
        }
        return true;
    }
}
```

