class Solution {
    public int reversePairs(int[] nums) {
        if(nums.length == 0)
            return 0;
        return reversePairsRecur(nums, 0, nums.length-1);
    }

    public int reversePairsRecur(int[] nums, int left, int right){
        if(left == right)
            return 0;
        int middle = (left + right) / 2;
        int n1 = reversePairsRecur(nums, left, middle);
        int n2 = reversePairsRecur(nums, middle+1, right);
        int result = n1 + n2;

        int i = left, j = middle+1;
        while (i<=middle){
            while (j<=right&&(long)nums[i] > 2*(long)nums[j]) j++;
            result +=j-middle-1;
            i++;
        }

        int[] sort = new int[right - left + 1];
        int p = 0;
        int p1 = left;
        int p2 = middle + 1;

        while ((p1 <= middle) || (p2 <= right)) {
            if (p1 > middle) {
                sort[p++] = (int) nums[p2++];
            } else if (p2 > right) {
                sort[p++] = (int) nums[p1++];
            } else {
                if (nums[p1] < nums[p2]) {
                    sort[p++] = (int) nums[p1++];
                } else {
                    sort[p++] = (int) nums[p2++];
                }
            }
        }

        for (int index = 0; index < right - left + 1; index++) {
            nums[left + index] = sort[index];
        }

        return result;
    }
}