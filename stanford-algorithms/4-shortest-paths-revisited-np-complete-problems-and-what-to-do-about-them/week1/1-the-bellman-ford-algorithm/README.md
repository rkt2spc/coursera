# The Bellman Ford Algorithm

Input:

- a directed graph $G=(V,E)$
- edge costs $c_e$ for each edge $e \in E$ (possibly negative)
- a source vertex $s \in V$

Goal:

- For all destinations $v \in V$ compute the length of the shortest $s$-$v$ path if there are no negative cycles
- Report if negative cycle(s) exist (excuse for failing to compute shortest paths)

Note:

- While computing cycle-free shortest path in a graph with negative cycles sounds tempting, it is a NP complete problem

## Optimal Sub-Structure

Let $G=(V,E)$ be directed graph with edge lengths $c_e$ for each edge $e \in E$ and source vertex $s \in V$

For every $v \in V$ and $i=\{1, 2, 3, 4, 5, ...\}$, let

$P =$ the shortest $s$-$v$ path with at most $i$ number of edges (cycles are permitted)

Case 1: if $P$ has $\le (i-1)$ edges, it is the shortest $s$-$v$ path with $\le (i-1)$ edges.  
Case 2: if $P$ has exactly $i$ edges with the last hop $(w,v)$ then $P' = P \setminus (w,v)$ is the shortest $s$-$w$ path with $\le (i-1)$ edges.

## Solution

**Notation:** Let $L_{i,v} =$ the minimum length of a $s$-$v$ path with $\le i$ number of edges.

- with cycles allowed
- defined as $+\infty$ if there are no $s$-$v$ paths with $\le i$ edges

**Recurrence:** For every $v \in V$, $i \in \{1, 2, 3, 4, ...\}$

$$
L_{i,v} = min \begin{cases}
    L_{(i-1),v} \\
    \min_{(w,v) \in E} \{L_{(i-1),w} + c_{(w,v)}\}
\end{cases}
$$

Assuming the graph doesn't contain negative cycles, the shortest path between any 2 vertices can only contain at most $n-1$ edges. Because if a path has $\ge n$ edges that means at least 1 vertex is visited twice, with no negative cycles visiting a vertex twice will just increase the path cost and it can't be the shortest path.

**Pseudo code:**

> let L = 2-D array (indexed by $i$ and $v$)  
>
> **Base case:**  
> L[0,s] = 0  
> L[0,v] = $+\infty$ for all $v \in V$  
>
> for i = 1 to n - 1  
> &emsp;for each $v \in V$  
> &emsp;&emsp;$L[i,v] = \min \begin{cases} L[i-1,v] \\ \min_{(w,v) \in E}\{L[i-1,w] + c_{(w,v)}\} \end{cases}$  
> return A[n - 1, v] with all $v \in V$

## Running time

- $n$ choices for $i$
- $\sum_{v \in V}\text{in-deg}(v) = m$ (number of edges) computations to find **min** in the inner *for* loop
  
=> $O(mn)$ time overall

## Optimizations

### Stopping Early

Suppose for some $j < n - 1$:  
$L[j,v] = L[j-1,v]$ for all $v \in V$  

=> for all $v \in V$, all future $L[i, v]$ will be the same $= L[j,v]$  
=> can safely halt.

### Detecting Negative Cycles

If the graph has no negative-cost cycles (that's reachable from $s$)  
<=> If we run the Bellman-Ford algorithm an extra iteration the cost shouldn't change (shouldn't decrease)  
<=> i.e $L[n-1,v] = L[n,v]$ for all $v \in V$

### Space Optimization

To compute $L[i,v]$ we only need $L[i-1,v]$ we can safely discard the result of all previous sub-problems $L[i-2,v], L[i-3,v], ...$ for all $v \in V$.

We only need a single array to keep track the result of the most recent sub-problems.
