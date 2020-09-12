# 637题解
考察点：二叉树的深度优先遍历与广度优先遍历

#### 方法一：深度优先遍历

使用深度优先搜索计算二叉树的层平均值，需要维护两个数组，counts 用于存储二叉树的每一层的节点数，sums 用于存储二叉树的每一层的节点值之和。搜索过程中需要记录当前节点所在层，如果访问到的节点在第 i 层，则将counts[i] 的值加 1，并将该节点的值加到 sums[i]。

遍历结束之后，第 i 层的平均值即为 sums[i]/counts[i]。

```java
class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Integer> counts = new ArrayList<Integer>();
        List<Double> sums = new ArrayList<Double>();
        dfs(root, 0, counts, sums);
        List<Double> averages = new ArrayList<Double>();
        int size = sums.size();
        for (int i = 0; i < size; i++) {
            averages.add(sums.get(i) / counts.get(i));
        }
        return averages;
    }

    public void dfs(TreeNode root, int level, List<Integer> counts, List<Double> sums) {
        if (root == null) {
            return;
        }
        if (level < sums.size()) {
            sums.set(level, sums.get(level) + root.val);
            counts.set(level, counts.get(level) + 1);
        } else {
            sums.add(1.0 * root.val);
            counts.add(1);
        }
        dfs(root.left, level + 1, counts, sums);
        dfs(root.right, level + 1, counts, sums);
    }
}
```

#### 方法二：广度优先遍历

也可以使用广度优先搜索计算二叉树的层平均值。从根节点开始搜索，每一轮遍历同一层的全部节点，计算该层的节点数以及该层的节点值之和，然后计算该层的平均值。

如何确保每一轮遍历的是同一层的全部节点呢？我们可以借鉴层次遍历的做法，广度优先搜索使用队列存储待访问节点，只要确保在每一轮遍历时，队列中的节点是同一层的全部节点即可。具体做法如下：

- 初始时，将根节点加入队列；


- 每一轮遍历时，将队列中的节点全部取出，计算这些节点的数量以及它们的节点值之和，并计算这些节点的平均值，然后将这些节点的全部非空子节点加入队列，重复上述操作直到队列为空，遍历结束。


由于初始时队列中只有根节点，满足队列中的节点是同一层的全部节点，每一轮遍历时都会将队列中的当前层节点全部取出，并将下一层的全部节点加入队列，因此可以确保每一轮遍历的是同一层的全部节点。

具体实现方面，可以在每一轮遍历之前获得队列中的节点数量 \textit{size}size，遍历时只遍历 \textit{size}size 个节点，即可满足每一轮遍历的是同一层的全部节点。

```java
public class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> averages = new ArrayList<>();
        Queue<TreeNode> trees = new LinkedList<>();
        trees.offer(root);
        while(!trees.isEmpty()){
            int currentSize = trees.size();
            double currentAverage = 0.0;
            for(int i=1;i <= currentSize;i++){
                TreeNode node = trees.poll();
                currentAverage += node.val;
                if(node.left!=null){
                    trees.offer(node.left);
                }
                if(node.right!=null){
                    trees.offer(node.right);
                }
            }
            averages.add(currentAverage/currentSize);
        }
        return averages;
    }
}
```

