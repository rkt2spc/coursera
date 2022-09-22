# The Knapsack Problem

**Inputs:**

- A set $S$ of $n$ items, each $i$-th item has value:
  - Value $v_i$ (non-negative)
  - Size $w_i$ (non-negative integer)
- Capacity $W$ (non-negative  integer)

**Output:**

- A subset $\Omega(S)$ of items that maximizes sum of values $\sum_{i \in \Omega(S)}v_i$
- However, sum of sizes must satisfies capacity $\sum_{i \in \Omega(S)}w_i \le W$

## Intuition

Consider an arbitrary item $e \in S$

If $e$ is **not** in the optimal solution, $e \notin \Omega(S)$  
-> $\Omega(S)$ must be the optimal solution for the remaining input items $S \setminus \{e\}$ with same capacity $W$

If $e$ is in the optimal solution, $e \in \Omega(S)$  
-> $\Omega(S) \setminus \{e\}$ must be the optimal solution for the  remaining input items $S \setminus \{e\}$ with capacity $W' = W - w_e$

## Solution

Let $V_{i,x}$ the total value of the best solution that:

- Uses only the first $i$ items
- The total size $\le x$

$$
V_{i,x} = max \begin{cases}
    V_{(i - 1),x} \\
    v_i + V_{(i - 1),(x - w_i)} \\
\end{cases}
$$

Edge case:
If $w_i > x$, must have $V_{i,x} = V_{(i - 1), x}$