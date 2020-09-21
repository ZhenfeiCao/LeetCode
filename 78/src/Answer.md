# 78题解
考察点：子集

#### 方法一：追加元素

首先来看一下非递归的解题思路，比如先加入一个空集让他成为新的子集，然后每遍历一个元素就在原来的子集的后面追加这个值。还以示例来分析下

![](https://pic.leetcode-cn.com/1600562274-OYGaTb-image.png)

```java
非递归写法：
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>(1 << nums.length);
    //先添加一个空的集合
    res.add(new ArrayList<>());
    for (int num : nums) {
        //每遍历一个元素就在之前子集中的每个集合追加这个元素，让他变成新的子集
        for (int i = 0, j = res.size(); i < j; i++) {
            //遍历之前的子集，重新封装成一个新的子集
            List<Integer> list = new ArrayList<>(res.get(i));
            //然后在新的子集后面追加这个元素
            list.add(num);
            //把这个新的子集添加到集合中
            res.add(list);
        }
    }
    return res;
}

递归写法：
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>(1 << nums.length);
    res.add(new ArrayList<>());
    recursion(nums, 0, res);
    return res;
}

public static void recursion(int[] nums, int index, List<List<Integer>> res) {
    //数组中的元素都访问完了，直接return
    if (index >= nums.length)
        return;
    int size = res.size();
    for (int j = 0; j < size; j++) {
        List<Integer> list = new ArrayList<>(res.get(j));
        //然后在新的子集后面追加一个值
        list.add(nums[index]);
        res.add(list);
    }
    //递归下一个元素
    recursion(nums, index + 1, res);
}
```

#### 方法二：回溯

可以把它想象成为一颗n叉树，通过DFS遍历这棵n叉树，他所走过的所有路径都是子集的一部分，看下代码

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
    //走过的所有路径都是子集的一部分，所以都要加入到集合中
    list.add(new ArrayList<>(tempList));
    for (int i = start; i < nums.length; i++) {
        //做出选择
        tempList.add(nums[i]);
        //递归
        backtrack(list, tempList, nums, i + 1);
        //撤销选择
        tempList.remove(tempList.size() - 1);
    }
}
```

#### 方法三：位运算

数组中的每一个数字都有选和不选两种状态，我们可以用0和1表示，0表示不选，1表示选择。如果数组的长度是n，那么子集的数量就是2^n。比如数组长度是3，就有8种可能，分别是

[0，0，0]

[0，0，1]

[0，1，0]

[0，1，1]

[1，0，0]

[1，0，1]

[1，1，0]

[1，1，1]

这里参照示例画个图来看下

![](https://pic.leetcode-cn.com/1600562175-KhRyHn-image.png)

```
public static List<List<Integer>> subsets(int[] nums) {
    //子集的长度是2的nums.length次方，这里通过移位计算
    int length = 1 << nums.length;
    List<List<Integer>> res = new ArrayList<>(length);
    //遍历从0到length中间的所有数字，根据数字中1的位置来找子集
    for (int i = 0; i < length; i++) {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < nums.length; j++) {
            //如果数字i的某一个位置是1，就把数组中对
            //应的数字添加到集合
            if (((i >> j) & 1) == 1)
                list.add(nums[j]);
        }
        res.add(list);
    }
    return res;
}
```

#### 方法四：递归

生成一个$2^n​$长的数组，数组的值从0到(2^n)-1。我们可以把它想象成为一颗二叉树，每个节点的子树都是一个可选一个不可选

![](https://pic.leetcode-cn.com/1600562394-enkdpc-image.png)

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    helper(res, nums, new ArrayList<>(), 0);
    return res;
}

private void helper(List<List<Integer>> res, int[] nums, List<Integer> list, int index) {
    //终止条件判断
    if (index == nums.length) {
        res.add(new ArrayList<>(list));
        return;
    }
    //每一个节点都有两个分支，一个选一个不选
    //走不选这个分支
    helper(res, nums, list, index + 1);
    //走选择这个分支
    list.add(nums[index]);
    helper(res, nums, list, index + 1);
    //撤销选择
    list.remove(list.size() - 1);
}
```

