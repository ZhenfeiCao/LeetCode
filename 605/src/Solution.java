class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int res = n, ptr = 0;
        int len = flowerbed.length;
        while (ptr<len&&res>0){
            if(flowerbed[ptr] == 0) {
                int num = 0;
                if(ptr == 0){
                    num++;
                }
                while (ptr<len&&flowerbed[ptr] == 0){
                    ptr++;
                    num++;
                }
                if(ptr == len){
                    num++;
                }
                res -= (num-1)/2;
            }
            ptr++;
        }
        return res<=0;
    }
}