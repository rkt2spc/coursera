# Weighted Independent Set in Path Graphs

**Input:** A path graph $G = (V, E)$ with non-negative weights on vertices.

**Desired Output:** a subset of non-adjacent vertices - an independent set - with maximum total weight.

## Simple Recursive Solution

Let's call:

- $n$ size of the path graph
- $v_i$ the i-th vertex in the path graph
- $w_i$ weight of the i-th vertex in the path graph
- $G_i$ the path graph containing vertex from 0-th to i-th inclusive.
- $G$ is equivalent to $G_n$
- $\Theta(G_i)$ is the minimum weight independent set of path graph $G_i$

Consider the last vertex in the path graph $v_n$

If $v_n \in \Theta(G)$:

- Then $v_{n-1} \notin \Theta(G)$ as $v_n$ and $v_{n-1}$ are adjacent
- Then $\Theta(G) = \Theta(\{v_0, v_1, v_2, ..., v_{n-2}\}) \cup \{v_n\}$
- In other words $\Theta(G) = \Theta(G_{n-2}) \cup \{v_n\}$

If $v_n \notin \Theta(G)$:

- Then $\Theta(G) = \Theta(G \setminus \{v_n\}) = \Theta(\{v_0, v_1, v_2, ..., v_{n-1}\})$
- In other words $\Theta(G) = \Theta(G_{n-1})$

Generalize the above observation, we'll get the following formula:

$$
\Theta(G_i) = \begin{cases}
    \Theta(G_{i-2}) \cup \{v_i\},& \text{if } v_i \in \Theta(G_i)\\
    \Theta(G_{i-1}),& \text{if } v_i \notin \Theta(G_i)\\
\end{cases}
$$

We can then implement this as a recursive bottom up problem. With $i$ from 0 to n, for each vertex $v_i$, we consider whether to add it to the maximum independent graph of not by seeing which of the 2 options produce the better weight.

If $w_i + \text{weight}(\Theta(G_{i-2})) \gt \text{weight}(\Theta(G_{i-1}))$

Then

- $\Theta(G_i) = \Theta(G_{n-2}) \cup \{v_i\}$

Else

- $\Theta(G_i) = \Theta(G_{n-1})$

## Dynamic Programming Optimization

Notice that we'll have to solve $\Theta(G_i)$ for $i = \{0, 1, ..., n\}$, possibly multiple times for each $i$.

We can cache this to avoid recomputation, that way we only have to solve $n$ problems, each with constant complexity.

So the total time complexity of this algorithm is $O(n)$
