class Solution {
    public String longestPalindrome(String s) {
        String result = "";
        for(int i=0;i<s.length();i++){
            int len1 = 0,len2 = 0;
            while((i-len1>=0)&&(i+len1<s.length())&&(s.charAt(i-len1)==s.charAt(i+len1))){
                len1++;
            }
            len1--;
            while((i-len2>=0)&&(i+1+len2<s.length())&&(s.charAt(i-len2)==s.charAt(i+1+len2))){
                len2++;
            }
            len2--;
            if(len1*2+1>result.length())
                result = s.substring(i-len1,i+len1+1);
            if(len2*2+2>result.length())
                result = s.substring(i-len2,i+2+len2);
        }
        return result;
    }
}
