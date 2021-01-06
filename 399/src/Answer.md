# 399题解
考察点：除法求值，图

#### 方法一：广度优先搜索

我们可以将整个问题建模成一张图：给定图中的一些点（变量），以及某些边的权值（两个变量的比值），试对任意两点（两个变量）求出其路径长（两个变量的比值）。

因此，我们首先需要遍历 $\textit{equations}$ 数组，找出其中所有不同的字符串，并通过哈希表将每个不同的字符串映射成整数。

在构建完图之后，对于任何一个查询，就可以从起点出发，通过广度优先搜索的方式，不断更新起点与当前点之间的路径长度，直到搜索到终点为止。

```java
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int nvars = 0;
        Map<String, Integer> variables = new HashMap<String, Integer>();

        int n = equations.size();
        for (int i = 0; i < n; i++) {
            if (!variables.containsKey(equations.get(i).get(0))) {
                variables.put(equations.get(i).get(0), nvars++);
            }
            if (!variables.containsKey(equations.get(i).get(1))) {
                variables.put(equations.get(i).get(1), nvars++);
            }
        }

        // 对于每个点，存储其直接连接到的所有点及对应的权值
        List<Pair>[] edges = new List[nvars];
        for (int i = 0; i < nvars; i++) {
            edges[i] = new ArrayList<Pair>();
        }
        for (int i = 0; i < n; i++) {
            int va = variables.get(equations.get(i).get(0)), vb = variables.get(equations.get(i).get(1));
            edges[va].add(new Pair(vb, values[i]));
            edges[vb].add(new Pair(va, 1.0 / values[i]));
        }

        int queriesCount = queries.size();
        double[] ret = new double[queriesCount];
        for (int i = 0; i < queriesCount; i++) {
            List<String> query = queries.get(i);
            double result = -1.0;
            if (variables.containsKey(query.get(0)) && variables.containsKey(query.get(1))) {
                int ia = variables.get(query.get(0)), ib = variables.get(query.get(1));
                if (ia == ib) {
                    result = 1.0;
                } else {
                    Queue<Integer> points = new LinkedList<Integer>();
                    points.offer(ia);
                    double[] ratios = new double[nvars];
                    Arrays.fill(ratios, -1.0);
                    ratios[ia] = 1.0;

                    while (!points.isEmpty() && ratios[ib] < 0) {
                        int x = points.poll();
                        for (Pair pair : edges[x]) {
                            int y = pair.index;
                            double val = pair.value;
                            if (ratios[y] < 0) {
                                ratios[y] = ratios[x] * val;
                                points.offer(y);
                            }
                        }
                    }
                    result = ratios[ib];
                }
            }
            ret[i] = result;
        }
        return ret;
    }
}

class Pair {
    int index;
    double value;

    Pair(int index, double value) {
        this.index = index;
        this.value = value;
    }
}
```

#### 方法二：Floyd 算法

对于查询数量很多的情形，如果为每次查询都独立搜索一次，则效率会变低。为此，我们不妨对图先做一定的预处理，随后就可以在较短的时间内回答每个查询。

在本题中，我们可以使用 $\text{Floyd}$ 算法，预先计算出任意两点之间的距离。

```java
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int nvars = 0;
        Map<String, Integer> variables = new HashMap<String, Integer>();

        int n = equations.size();
        for (int i = 0; i < n; i++) {
            if (!variables.containsKey(equations.get(i).get(0))) {
                variables.put(equations.get(i).get(0), nvars++);
            }
            if (!variables.containsKey(equations.get(i).get(1))) {
                variables.put(equations.get(i).get(1), nvars++);
            }
        }
        double[][] graph = new double[nvars][nvars];
        for (int i = 0; i < nvars; i++) {
            Arrays.fill(graph[i], -1.0);
        }
        for (int i = 0; i < n; i++) {
            int va = variables.get(equations.get(i).get(0)), vb = variables.get(equations.get(i).get(1));
            graph[va][vb] = values[i];
            graph[vb][va] = 1.0 / values[i];
        }

        for (int k = 0; k < nvars; k++) {
            for (int i = 0; i < nvars; i++) {
                for (int j = 0; j < nvars; j++) {
                    if (graph[i][k] > 0 && graph[k][j] > 0) {
                        graph[i][j] = graph[i][k] * graph[k][j];
                    }
                }
            }
        }

        int queriesCount = queries.size();
        double[] ret = new double[queriesCount];
        for (int i = 0; i < queriesCount; i++) {
            List<String> query = queries.get(i);
            double result = -1.0;
            if (variables.containsKey(query.get(0)) && variables.containsKey(query.get(1))) {
                int ia = variables.get(query.get(0)), ib = variables.get(query.get(1));
                if (graph[ia][ib] > 0) {
                    result = graph[ia][ib];
                }
            }
            ret[i] = result;
        }
        return ret;
    }
}
```

#### 方法三：带权并查集

我们还可以考虑以并查集的方式存储节点之间的关系。设节点 $x$ 的值（即对应变量的取值）为 $v[x]$。对于任意两点 $x,y$，假设它们在并查集中具有共同的父亲 $f$，且 $v[x]/v[f] = a, v[y]/v[f]=b$，则 $v[x]/v[y]=a/b$。

在观察到这一点后，就不难利用并查集的思想解决此题。对于每个节点 $x$ 而言，除了维护其父亲 $f[x]$ 之外，还要维护其权值 $w$，其中「权值」定义为节点 $x$ 的取值与父亲 $f[x]$ 的取值之间的比值。换言之，我们有

$w[x] = \frac{v[x]}{v[f[x]]}$

下面，我们对并查集的两种操作的实现细节做出讨论。

当查询节点 $x$ 父亲时，如果 $f[x] \ne x$，我们需要先找到 $f[x]$ 的父亲 $\textit{father}$，并将 $f[x]$ 更新为 $\textit{father}$。此时，我们有

$\begin{aligned} w[x] &\leftarrow \frac{v[x]}{v[\textit{father}]} \\ &= \frac{v[x]}{v[f[x]]} \cdot \frac{v[f[x]]}{v[\textit{father}]} \\ &= w[i] \cdot w[f[x]] \end{aligned}$

也就是说，我们要将 $w[x]$ 更新为 $w[x] \cdot w[f[x]]$。

当合并两个节点 $x,y$ 时，我们首先找到两者的父亲 $f_x, f_y$，并将 $f[f_x]$ 更新为 $f_y$ 。此时，我们有

$\begin{aligned} w[f_x] &\leftarrow \frac{v[f_x]}{v[f_y]} \\ &= \frac{v[x]/w[x]}{v[y]/w[y]} \\ &= \frac{v[x]}{v[y]} \cdot \frac{w[y]}{w[x]} \end{aligned}$

也就是说，当在已有的图中添加一条方程式 $\frac{v[x]}{v[y]}=k $时，需要将 $w[f_x]$ 更新为 $k\cdot \frac{w[y]}{w[x]}$。

```java
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int nvars = 0;
        Map<String, Integer> variables = new HashMap<String, Integer>();

        int n = equations.size();
        for (int i = 0; i < n; i++) {
            if (!variables.containsKey(equations.get(i).get(0))) {
                variables.put(equations.get(i).get(0), nvars++);
            }
            if (!variables.containsKey(equations.get(i).get(1))) {
                variables.put(equations.get(i).get(1), nvars++);
            }
        }
        int[] f = new int[nvars];
        double[] w = new double[nvars];
        Arrays.fill(w, 1.0);
        for (int i = 0; i < nvars; i++) {
            f[i] = i;
        }

        for (int i = 0; i < n; i++) {
            int va = variables.get(equations.get(i).get(0)), vb = variables.get(equations.get(i).get(1));
            merge(f, w, va, vb, values[i]);
        }
        int queriesCount = queries.size();
        double[] ret = new double[queriesCount];
        for (int i = 0; i < queriesCount; i++) {
            List<String> query = queries.get(i);
            double result = -1.0;
            if (variables.containsKey(query.get(0)) && variables.containsKey(query.get(1))) {
                int ia = variables.get(query.get(0)), ib = variables.get(query.get(1));
                int fa = findf(f, w, ia), fb = findf(f, w, ib);
                if (fa == fb) {
                    result = w[ia] / w[ib];
                }
            }
            ret[i] = result;
        }
        return ret;
    }

    public void merge(int[] f, double[] w, int x, int y, double val) {
        int fx = findf(f, w, x);
        int fy = findf(f, w, y);
        f[fx] = fy;
        w[fx] = val * w[y] / w[x];
    }

    public int findf(int[] f, double[] w, int x) {
        if (f[x] != x) {
            int father = findf(f, w, f[x]);
            w[x] = w[x] * w[f[x]];
            f[x] = father;
        }
        return f[x];
    }
}
```

