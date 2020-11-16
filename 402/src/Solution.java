class Solution {
    public String removeKdigits(String num, int k) {
        String res = num;
        if(k==num.length()) return "0";
        int index = 0;
        while (k>0){
            while (index+1<res.length()&&res.charAt(index)<=res.charAt(index+1)) index++;
            if(index+1==res.length()){
                return res.substring(0, res.length()-k);
            }else{
                res = res.substring(0,index) + res.substring(index + 1);
                if(index>0)
                    index--;
            }
            k--;
        }
        while (res.length()>1&&res.charAt(0)=='0')
            res = res.substring(1);
        return res;
    }
}