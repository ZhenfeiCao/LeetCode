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