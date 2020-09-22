# 958题解
#### 方法一：后序遍历+状态转移

##### 1. 确定遍历方式

在安排选择摄像头的位置的时候，我们要从底向上进行推导，因为尽量让叶子节点的父节点安装摄像头，这样摄像头的数量才是最少的

如何从低向上推导呢？

就是后序遍历也就是左右中的顺序，这样就可以从下到上进行推导了。

##### 2.需要状态转移的方程

确定了遍历顺序，再看看这个状态应该如何转移，先来看看每个节点可能有几种状态：

可以说有如下三种：

该节点无覆盖
本节点有摄像头
本节点有覆盖无摄像头
我们分别有三个数字来表示：

0：该节点无覆盖
1：本节点有摄像头
2：本节点有覆盖无摄像头
大家应该找不出第四个节点的状态了。

那么问题来了，空节点究竟是哪一种状态呢？ 空节点表示无覆盖？ 表示有摄像头？还是有覆盖呢？

回归本质，为了让摄像头数量最少，我们要尽量让叶子节点的父节点安装摄像头，这样才能摄像头的数量最少。

那么空节点不能是无覆盖的状态，这样叶子节点就可以放摄像头了，空节点也不能是有摄像头的状态，这样叶子节点的父节点就没有必要放摄像头了，而是可以把摄像头放在叶子节点的爷爷节点上。

所以空节点的状态只能是有覆盖，这样就可以在叶子节点的父节点放摄像头了

接下来就是递推关系。

递归的终止条件应该是遇到了空节点，此时应该返回2（有覆盖），原因上面已经解释过了。

主要有以下四类情况：

###### 1.左右节点都有覆盖无摄像头

左孩子有覆盖，右孩子有覆盖，那么此时中间节点应该就是无覆盖的状态了。

![](https://pic.leetcode-cn.com/1600744629-AgRMTO-968.%E7%9B%91%E6%8E%A7%E4%BA%8C%E5%8F%89%E6%A0%912.png)

###### 2. 左右节点至少有一个无覆盖的情况

如果是以下情况，则中间节点（父节点）应该放摄像头：

left == 0 && right == 0 左右节点无覆盖
left == 1 && right == 0 左节点有摄像头，右节点无覆盖
left == 0 && right == 1 左节点有无覆盖，右节点摄像头
left == 0 && right == 2 左节点无覆盖，右节点覆盖
left == 2 && right == 0 左节点覆盖，右节点无覆盖

这个不难理解，毕竟有一个孩子没有覆盖，父节点就应该放摄像头。

此时摄像头的数量要加一，并且return 1，代表中间节点放摄像头。

###### 3. 左右节点至少有一个有摄像头

如果是以下情况，其实就是 左右孩子节点有一个有摄像头了，那么其父节点就应该是2（覆盖的状态）

left == 1 && right == 2 左节点有摄像头，右节点有覆盖
left == 2 && right == 1 左节点有覆盖，右节点有摄像头
left == 1 && right == 1 左右节点都有摄像头

###### 4. 根节点判断

以上都处理完了，递归结束之后，可能头结点 还有一个无覆盖的情况，

![](https://pic.leetcode-cn.com/1600744659-SFiBRU-968.%E7%9B%91%E6%8E%A7%E4%BA%8C%E5%8F%89%E6%A0%913.png)

```java
class Solution {
    int result = 0;
    public int minCameraCover(TreeNode root) {
        int rootState = minCam(root);
        if(rootState == 0){
            result++;
        }
        return result;
    }

    public int minCam(TreeNode root) {
        if(root == null)
            return 2;
        int left = minCam(root.left);
        int right = minCam(root.right);
        if(left == 0 || right == 0) {
            result++;
            return 1;
        }
        if(left == 1 || right == 1){
            return 2;
        }
        if(left == 2 && right == 2){
            return 0;
        }
        return -1;
    }
}
```

#### 方法二：后序遍历+距离计算

想要最大化摄像头范围

如果从上往下捋，上面最大化了，下面因为分叉，为了补齐，需要多个摄像头
如果从下往上捋，下面最大化，上面由于分支合并，就算要补齐，补的个数也少
所以应该后序遍历

将后序遍历的返回值定义为与摄像头的距离

如果这里安装了摄像头，就返回 0
其父节点的距离就是 0 + 1 = 1
祖父节点的距离就是 0 + 1 + 1 = 2
当距离为 2 时，这里就覆盖不到了，再上一层就该安装新摄像头了
安装时，记录安装个数

由于可能有左右两个节点

对于自身与摄像头的距离为：ret = min(l, r) + 1
而对于是否要安装摄像头，要考虑其子节点是否已经无法接受照射了
所以要判断：if (max(l, r) == 2)

注意根节点的距离是 2 时，没有上一层给安摄像头。

```java
class Solution {
    int result = 0;
    public int minCameraCover(TreeNode root) {
        int rootState = minCam(root);
        if(rootState == 2){
            result++;
        }
        return result;
    }

    public int minCam(TreeNode root) {
        if(root == null)
            return 1;
        int left = minCam(root.left);
        int right = minCam(root.right);
        if(Math.max(left, right) == 2){
            result++;
            return 0;
        }else{
            return Math.min(left, right) + 1;
        }
    }
}
```

