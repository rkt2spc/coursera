# Optimal Binary Search Trees

Given a set of items and their known frequencies of access. What is the optimal search trees that minimize average search time?

**Inputs:**

- Frequencies $p_1, p_2, ..., p_n$ for items $1,2,...,n$
- Assume that items are in sorted order $1 < 2 < 3 < ... < n$

**Goal:**

- Compute a valid binary search tree that minimize the weighted average search time.

$$
C(T) = \sum_{\text{item i}} p_i \cdot [\text{search time for i in T}]
$$

Example: if $T$ is a red-black tree then $C(T) = O(\log{n})$

## Intuition

Let $T$ be an optimal binary search tree for keys $\{ 1, 2, ..., n \}$ with frequencies $p_1, p_2, ..., p_n$. Suppose $T$ has root $r$.

Then:

- The left subtree $T_1$ must be optimal for keys $\{ 1, 2, ..., r-1 \}$
- The right subtree $T_2$ must be optimal for keys $\{ r+1, r+2, ..., n \}$

## Solution

**Notation:**

For $1 \le i \le j \le n$, let $C_{ij}$ weighted search cost of an optimal BST for the items $\{ i, i+1, ..., j-1, j \}$ with probabilities $p_i, p_{i+1}, ..., p_{j-1}, p_j$

**Recurrence:**

For every $1 \le i \le j \le n$

$$
C_{ij} = \min^j_{r=i} \{ \sum^j_{k=i} p_k + C_{i,r-1} + C_{r+1,j} \}
$$

Solve the smallest subproblems with fewest number (j-i+1) of items first.

> let A = 2-D array  
> for s = 0 to n - 1  
> &emsp;for i = 1 to n  
> &emsp;&emsp;$A[i,i+s] = \min^{i+s}_{r=i} \{ \sum^{i+s}_{k=i} p_k + A[i,r-1] + A[r+1,i+s] \}$  
> return A[1, n]

## Running time

- $O(n^2)$ sub-problems
- $O(j-i)$ to compute $A[i,j]$
  
=> $O(n^3)$ time overall

Read `[Knuth '71, Yao '80]` for an optimized version of this DP algorithm correctly fills up entire table in only $O(n^2)$ time [$O(1)$ on average per sub-problem]

