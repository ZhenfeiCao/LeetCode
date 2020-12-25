class Solution {
    public int candy(int[] ratings) {
        int[] res = new int[ratings.length];
        int ptr = 0;
        res[0] = 1;
        while (ptr<ratings.length-1){
            int prePos = ptr;
            if(ratings[ptr+1] > ratings[ptr]){
                while (ptr<ratings.length-1&&ratings[ptr+1]>ratings[ptr]){
                    res[ptr+1] = res[ptr] + 1;
                    ptr++;
                }
            }else if(ratings[ptr+1] < ratings[ptr]){
                while (ptr<ratings.length-1&&ratings[ptr+1]<ratings[ptr]){
                    ptr++;
                }
                int tempPtr = ptr-1;
                res[ptr] = 1;
                while (tempPtr>prePos){
                    res[tempPtr] = res[tempPtr+1] + 1;
                    tempPtr--;
                }
                res[prePos] = Math.max(res[prePos], res[prePos+1] +1);
            }else{
                res[ptr+1] = 1;
                ptr++;
            }
        }
        int total = 0;
        for(int i:res)
            total+=i;
        return total;
    }
}