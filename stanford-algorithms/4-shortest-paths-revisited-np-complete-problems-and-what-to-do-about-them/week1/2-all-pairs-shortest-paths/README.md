# All Pairs Shortest Paths

Input:

- a directed graph $G=(V,E)$
- edge costs $c_e$ for each edge $e \in E$ (possibly negative)

Goal:

- For all vertices pair $i,j \in V$ compute the length of the shortest $i$-$j$ path

## Optimal Sub-Structure

Let:

- $G=(V,E)$ be a directed graph with edge lengths $c_e$ for each edge $e \in E$
- $V^k$ be the first $k$ vertices in $V$
- $P_{i,j,k}$ be the shortest path between source vertex $i \in V$, destination vertex $j \in V$ with all internal path vertices $\in V^k$

Suppose $G$ has no negative cycles. Then $P_{i,j,k}$ is cycle-free.

Case 1: If vertex $k$ is not internal to $P_{i,j,k}$, then $P_{i,j,k}$ is a shortest cycle-free $i$-$j$ path with all internal vertices $\in V^{k-1}$

Case 2: If vertex $k$ is internal to $P_{i,j,k}$, then:

- $P_{i,k,k-1}$ is the shortest (cycle-free) $i$-$k$ path with all internal vertices $\in V^{k-1}$
- $P_{k,j,k-1}$ is the shortest (cycle-free) $k$-$j$ path with all internal vertices $\in V^{k-1}$

## Floyd Warshall Algorithm

Let's $A[i,j,k]$ be the length of the shortest (cycle-free) $i$-$j$ path where all internal vertices belongs to the first $k$ vertices.

Base cases, When $k = 0$:

$$
A[i,j,0] = \begin{cases}
    0& \text{if } i = j\\
    c_{(i,j)}& \text{if } (i,j) \in E\\
    +\infty& \text{if } i \neq j \text{ and } (i,j) \notin E
\end{cases}
$$

Recurrence:

$$
A[i,j,k] = \min \begin{cases}
A[i,j,k-1]\\
A[i,k,k-1] + A[k,j,k-1]
\end{cases}
$$

### Running time

**Time:** $O(n^3)$  

### Detecting Negative Cycles

If a negative cycle exists, at least one of $A[i,i,n]$ will be $\lt 0$ for a vertex $i \in V$ at the end of the algorithm.

Explanation: if a negative cycle exist then there will be a path from a vertex to itself with negative cost.

### Reconstructing Shortest Paths

We need to compute $B[i,j] =$ max label of an internal node on a shortest $i$-$j$ path for all $i,j \in V$

Reset $B[i,j] = k$ if the 2nd case of the recurrence used to compute $A[i,j,k]$

Shortest $i$-$j$ path is the combination of the shortest paths between $i$-$B[i,j]$ and $B[i,j]$-$j$.  
Repeat recursively to compute the whole path.

## Re-weighting

We can solve the All Pairs Shortest Paths (APSP) problem by invoking a Single Source Shortest Paths (SSSP) algorithm $n$ times (number of vertices), once for each vertex.

If we're going to reuse the Bellman-Ford algorithm we will end up with the running time of $O(m \cdot n^2)$  
If we're going to reuse the Dijkstra algorithm we will end up with the running time of $O(n \cdot m \log{n})$

You can see that we can achieve better running time by reusing Dijkstra algorithm. The problem is Dijkstra only works on graph with non-negative edge length.

How can we transform all negative weighted edges to non-negative to reuse Dijkstra algorithm?

!!!: Adding an equal amount of weight to all edges won't work because the constant addition multiplied by the amount of edges on each path could change the shortest path result.

## Johnson Algorithm

Assuming that we have an imaginary vertex $s \notin V$ that is connected to all vertices in $V$ with 0 directed edge weight.  
And $p_v$ is the length of shortest paths $s$-$v$

For every edge $(u,v) \in E$

Let $P_u$ be the shortest $s$-$u$ path with length $p_u$  
Then $P_u + (u,v)$ is a $s$-$v$ path with length $p_u + c_{uv}$  

As $p_v$ is the length of the shortest $s$-$v$ path:  
$p_v \leq p_u + c_{uv} \leftrightarrow c_{uv} + p_u - p_v \geq 0$

The idea of the Johnson algorithm is to re-weight each edge $(u,v)$ of the graph from $c_{uv}$ to $c'_{uv} = c_{uv} + p_u - p_v$

Since we've proven that $c_{uv} + p_u - p_v \geq 0 \leftrightarrow c'_{uv} \geq 0$  
We can simply run Dijkstra $n$ times on this new re-weighted graph as all edges have non-negative weight.

This re-weighting scheme works because:

- The length of all paths from any vertex $i$ to vertex $j$ is only affected by the same amount $p_i - p_j$
  - Let's say a path from $i$ to $j$ = $(i,k) + (k,j)$ then it's re-weighted length is $c_{ik} + p_i - p_k + c_{kj} + p_k - p_j = c_{ik} + c_{kj} + p_i - p_j$
  - This is equal to it's original path length plus $p_i - p_j$
- Since all $i$-$j$ paths length are shifted by the same amount $p_i - p_j$ the final shortest path won't change

### Steps

1. Form $G'$ by adding a vertex $s$ and edges $(s,v)$ with length $0$ for each vertex $v \in V$
2. Run Bellman-Ford on $G'$ with source vertex $s$
   - If negative-cycles are detected (which must lie in $G$), halt and report.
3. For each $v \in V$ define $p_v$ as length of the shortest $s$-$v$ path in G'.  
   For each edge $(u, v) \in E$ define $c'_{uv} = c_{uv} + p_u - p_v$
4. For each vertex $u \in V$  
   Run Dijkstra algorithm in $G$ with edge weight $c'_e$ and source vertex $u$  
   to compute the shortest path distance $d'(u,v)$ for each vertex $v \in V$
5. For each pair $u,v \in V$ return the shortest path distance $d(u,v) = d'(u,v) - p_u + p_v$

### Running time

**Time:** $O(n \cdot m\log n)$  
Running Dijkstra $n$-times
