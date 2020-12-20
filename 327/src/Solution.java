public class Solution {

    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;

        if (n == 0) {
            return 0;
        }

        long[] sum = new long[n + 1];
        long s = 0;

        for (int i = 0; i < n; i++) {
            s += nums[i];
            sum[i + 1] = s;
        }

        return countRangeSumMergeSortRecursize(sum, 0, n, lower, upper);
    }

    int countRangeSumMergeSortRecursize(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return 0;
        }

        int middle = (left + right) / 2;
        int result = 0;

        result += countRangeSumMergeSortRecursize(sum, left, middle, lower, upper);
        result += countRangeSumMergeSortRecursize(sum, middle + 1, right, lower, upper);

        int i = left;
        int l = middle + 1;
        int r = middle + 1;

        while (i <= middle) {
            while ((l <= right) && (sum[l] - sum[i] < lower)) {
                l++;
            }

            while ((r <= right) && (sum[r] - sum[i] <= upper)) {
                r++;
            }

            result += (r - l);
            i++;
        }

        int[] sort = new int[right - left + 1];
        int p = 0;
        int p1 = left;
        int p2 = middle + 1;

        while ((p1 <= middle) || (p2 <= right)) {
            if (p1 > middle) {
                sort[p++] = (int) sum[p2++];
            } else if (p2 > right) {
                sort[p++] = (int) sum[p1++];
            } else {
                if (sum[p1] < sum[p2]) {
                    sort[p++] = (int) sum[p1++];
                } else {
                    sort[p++] = (int) sum[p2++];
                }
            }
        }

        for (int j = 0; j < right - left + 1; j++) {
            sum[left + j] = sort[j];
        }

        return result;
    }
}