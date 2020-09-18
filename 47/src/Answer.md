# 47.题解
考察点：回溯与剪枝

#### 方法一：回溯与剪枝

要解决重复问题，我们只要设定一个规则，保证在填第 idx 个数的时候重复数字只会被填入一次即可。而在本题解中，我们选择对原数组排序，保证相同的数字都相邻，然后每次填入的数一定是这个数所在重复数集合中「从左往右第一个未被填过的数字」，即如下的判断条件：

```
if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1])) {
                continue;
}
```

vis[i]为true表示该位置已经被填入空格不能重复填到新的空格中（在46题中就已经存在的条件），而i > 0 && nums[i] == nums[i - 1] && !vis[i - 1]  i>0是为了nums[i-1]有意义，nums[i]==nums[i-1]表示填入的数字是与之前数字相同的数字，!vis[i-1] 因为vis中的值在回溯后被填false，所以如果!vis[i-1]为true则表明nums[i-1]已经被填入过这个位置了，而nums[i]又与nums[i-1]相同所以不能被填到这个位置中了。

```java
class Solution {
    boolean[] vis;

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> perm = new ArrayList<Integer>();
        vis = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, ans, 0, perm);
        return ans;
    }

    public void backtrack(int[] nums, List<List<Integer>> ans, int idx, List<Integer> perm) {
        if (idx == nums.length) {
            ans.add(new ArrayList<Integer>(perm));
            return;
        }
        for (int i = 0; i < nums.length; ++i) {
            if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1])) {
                continue;
            }
            perm.add(nums[i]);
            vis[i] = true;
            backtrack(nums, ans, idx + 1, perm);
            vis[i] = false;
            perm.remove(idx);
        }
    }
}
```

